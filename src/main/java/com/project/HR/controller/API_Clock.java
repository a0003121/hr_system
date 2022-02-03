package com.project.HR.controller;

import com.project.HR.dao.CalendarDAO;
import com.project.HR.dao.ClockRawDAO;
import com.project.HR.dao.ClockTimeDAO;
import com.project.HR.dao.EmployeeDAO;
import com.project.HR.util.Constant;
import com.project.HR.vo.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@RestController
//@EnableJpaAuditing//啟用審計(Auditing)
public class API_Clock {
    @Autowired
    CalendarDAO calendarDAO;

    @Autowired
    ClockTimeDAO clockTimeDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    ClockRawDAO clockRawDAO;

    /////////////////打卡紀錄///////////////////

    //取得打卡紀錄
    @GetMapping("/clock")
    public String getClock(String start, String end) throws ParseException {
//        Page<ClockTime> pageResult = clockTimeDAO.findAll(PageRequest.of(0, 10, Sort.by("clockDate").ascending()));
        SimpleDateFormat converter = new SimpleDateFormat("yyyy/MM/dd");
        long dateStart = converter.parse(start).getTime();
        long dateEnd = converter.parse(end).getTime();

        JSONArray resultArr = new JSONArray();

        //取得所以日曆日期
        List<Calendar> calendarList = calendarDAO.findByDateBetween(new java.sql.Date(dateStart),
                new java.sql.Date(dateEnd), Sort.by("date").ascending());
        //取得員工名單
        List<Employee> empList = employeeDAO.findByStatus(Constant.STATUS_IN);
        for (Employee employee : empList) {
            JSONObject empObj = new JSONObject();
            empObj.put("empNo", employee.getEmpNo());
            empObj.put("name", employee.getName());
            Dept dept = employee.getDeptName();
            empObj.put("deptName", dept.getName());

            JSONArray dataArr = new JSONArray();
            //取得員工打卡紀錄
            List<ClockTime> clockList = clockTimeDAO.findByEmpNoAndClockDateBetween(employee.getEmpNo(), new Date(dateStart), new Date(dateEnd), Sort.by("clockDate").ascending());
            int index = 0;

            for (Calendar calendar : calendarList) {
                JSONObject tempObj = new JSONObject(calendar);
                //如果比對日是放假日
                if (calendar.getType() == Constant.CALENDER_OFFDAY) {
                    tempObj.put("off", true);
                    dataArr.put(tempObj);
                    continue;
                }


                //如果比對日有員工刷卡紀錄
                if (index + 1 <= clockList.size()) {
                    LocalDate clockDate = clockList.get(index).getClockDate().toLocalDate();
                    LocalDate calDate = calendar.getDate().toLocalDate(); // Pass a time zone to get current date in UTC for fair comparison.
                    if (calDate.isEqual(clockDate)) {
                        tempObj.put("startTime", clockList.get(index).getStartTime());
                        tempObj.put("endTime", clockList.get(index).getEndTime());
                        tempObj.put("clockDate", clockList.get(index).getClockDate());
                        dataArr.put(tempObj);
                        index++;
                    } else {
                        tempObj.put("no_record", true);
                        dataArr.put(tempObj);
                    }

                } else { //如果比對日無員工刷卡紀錄
                    tempObj.put("no_record", true);
                    dataArr.put(tempObj);
                }
            }
            empObj.put("data", dataArr);
            resultArr.put(empObj);
        }


        return resultArr.toString();
    }

    //檔案範本
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        //建立工作表
        Sheet sheet = workbook.createSheet("ClockTime");
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 6000);
        Row header = sheet.createRow(0);

        //設定內容
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("員工編號");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("員工姓名");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("打卡時間");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row row = sheet.createRow(1);
        Cell cell = row.createCell(0);
        cell.setCellValue("ex:999");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("ex:王小明");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("ex:2022/2/2 18:00");
        cell.setCellStyle(style);

        //傳到前端
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("打卡紀錄範本.xlsx", "UTF-8"));
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    //打卡紀錄上傳
    @PostMapping("/fileUpload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipart, Boolean send) throws IOException {
        Workbook workbook = new XSSFWorkbook(multipart.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        int totalDataRow = sheet.getLastRowNum();
        int successDataRow = 0;
        JSONArray datas = new JSONArray();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            String name = "";
            int empNo = 0;
            Timestamp date = null;
            Boolean checkpoint = false;
            JSONObject eachData = new JSONObject();
            for (Cell cell : row) {
//                System.out.println("column:" + cell.getColumnIndex() + ", row: " + cell.getRowIndex() + "value: " + cell);
                checkpoint = false;

                try {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            eachData.put("empNo", translateData(cell));
                            empNo = (int) cell.getNumericCellValue();
                            break;
                        case 1:
                            eachData.put("name", translateData(cell));
                            name = cell.getStringCellValue();
                            break;
                        case 2:
                            eachData.put("date", translateData(cell));
                            date = new Timestamp(cell.getDateCellValue().getTime());
                            break;
                    }
                } catch (IllegalStateException e) {
                    checkpoint = true;
                }
            }

            if (checkpoint) {
                if (name.contains("ex:")) {
                    totalDataRow--;
                } else {
                    eachData.put("error", true);
                    eachData.put("error-message", "unknown");
                    datas.put(eachData);
                }
                continue;
            }
            if (employeeDAO.findByEmpNo(empNo).size() != 0) {
                if (send)
                    clockRawDAO.save(new ClockRaw(0, date, empNo));

                successDataRow++;
            } else {
                eachData.put("error", true);
                eachData.put("error-message", "empNo");
            }

            datas.put(eachData);

        }

        workbook.close();

        JSONObject result = new JSONObject();
        result.put("totalData", totalDataRow);
        result.put("success", successDataRow);
        result.put("result", datas);
        return result.toString();
    }

    //excel資料轉譯
    public String translateData(Cell cell) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return sdf.format(cell.getDateCellValue());
                } else {
                    return Integer.toString((int) cell.getNumericCellValue());
                }
            default:
                return cell.toString();
        }
    }
}
