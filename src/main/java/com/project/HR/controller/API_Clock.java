package com.project.HR.controller;

import com.project.HR.dao.*;
import com.project.HR.service.LeaveService;
import com.project.HR.util.Constant;
import com.project.HR.vo.Calendar;
import com.project.HR.vo.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
import java.util.*;

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

    @Autowired
    EmployeeLeaveDAO employeeLeaveDAO;

    @Autowired
    LeaveService leaveService;

    /////////////////打卡紀錄///////////////////

    //取得個人某日的打卡和請假紀錄
    @GetMapping("/clockRecord")
    public String getRecordByEmpnoAndDate(int empNo, String date) throws ParseException {
        JSONArray clockResult = new JSONArray();
        JSONArray leaveResult = new JSONArray();

        SimpleDateFormat converter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat converter2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat converter3 = new SimpleDateFormat("HH:mm");
        Timestamp timeStart = new Timestamp(converter1.parse(date + " 00:00").getTime());
        Timestamp timeEnd = new Timestamp(converter1.parse(date + " 24:00").getTime());
        //打卡紀錄
        List<ClockRaw> recordList = clockRawDAO.findByTimeBetweenAndEmpNoOrderByTimeAsc(timeStart, timeEnd, empNo);
        for (ClockRaw clockRaw : recordList) {
            JSONObject obj = new JSONObject();
            obj.put("id", clockRaw.getId());
            obj.put("date", converter2.format(clockRaw.getTime()));
            obj.put("time", converter3.format(clockRaw.getTime()));
            clockResult.put(obj);
        }
        //請假紀錄
        List<EmployeeLeave> leaveList = employeeLeaveDAO.findByEmployeeIdAndLeaveDate(empNo, converter2.parse(date));
        for (EmployeeLeave employeeLeave : leaveList) {
            leaveResult.put(new JSONObject(employeeLeave));
        }
        JSONObject result = new JSONObject();
        result.put("clock", clockResult);
        result.put("leave", leaveResult);
        return result.toString();
    }

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

                //如果比對日有員工刷卡紀錄
                if (index + 1 <= clockList.size()) {
                    LocalDate clockDate = clockList.get(index).getClockDate().toLocalDate();
                    LocalDate calDate = calendar.getDate().toLocalDate(); // Pass a time zone to get current date in UTC for fair comparison.
                    if (calDate.isEqual(clockDate)) {
                        //SimpleDateFormat converter = new SimpleDateFormat("yyyy/MM/dd");

                        tempObj.put("startTime", clockList.get(index).getStartTime());
                        tempObj.put("endTime", clockList.get(index).getEndTime());
                        tempObj.put("clockDate", clockList.get(index).getClockDate());
                        tempObj.put("status", clockList.get(index).getStatus());

                        //刪除沒資料的資料
                        if (clockList.get(index).getStartTime() == null && clockList.get(index).getEndTime() == null) {
                            clockTimeDAO.deleteById(clockList.get(index).getId());
                        }

                        index++;


                    } else {
                        tempObj.put("no_record", true);
                    }
                } else { //如果比對日無員工刷卡紀錄
                    tempObj.put("no_record", true);
                }
                //如果比對日是放假日
                if (calendar.getType() == Constant.CALENDER_OFFDAY) {
                    tempObj.put("off", true);
                    tempObj.put("no_record", false);
                }
                dataArr.put(tempObj);


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
                } catch (IllegalStateException | NullPointerException e) {
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

    //打卡比對
    @Transactional
    @GetMapping("/compareClock")
    public String compareClock(String end, String start) throws ParseException {
        SimpleDateFormat converter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Timestamp timeStart = new Timestamp(converter.parse(start + " 00:00").getTime());
        Timestamp timeEnd = new Timestamp(converter.parse(end + " 24:00").getTime());
        SimpleDateFormat converter2 = new SimpleDateFormat("yyyy/MM/dd");
        List<ClockRaw> rawList = clockRawDAO.findByTimeBetweenOrderByEmpNoAscTimeAsc(timeStart, timeEnd);

        //取得員工編號清單
        Set<Integer> empNoList = new HashSet<>();
        for (ClockRaw clockRaw : rawList) {
            empNoList.add(clockRaw.getEmpNo());
        }

        for (int empNo : empNoList) {
            Map<String, List<ClockRaw>> timeMap = new HashMap<>();
            for (ClockRaw clockRaw : rawList) {
                if (clockRaw.getEmpNo() == empNo) {
                    //找出相同日期的存在一起
                    Timestamp timeFromData = clockRaw.getTime();
                    String date = converter2.format(new java.util.Date(timeFromData.getTime()));

                    if (!timeMap.containsKey(date)) {
                        List<ClockRaw> temp = new ArrayList<>();
                        temp.add(clockRaw);
                        timeMap.put(date, temp);
                    } else {
                        timeMap.get(date).add(clockRaw);
                    }
                }
            }
            //更新資料庫資料
            for (String belongDate : timeMap.keySet()) {
                //先刪舊資料
                clockTimeDAO.deleteByEmpNoAndClockDate(empNo, converter2.parse(belongDate));

                //新增資料
                List<ClockRaw> eachDateList = timeMap.get(belongDate);
                ClockTime clockTime = new ClockTime(0, new Date(converter2.parse(belongDate).getTime()), empNo, null, null, null, null, null);
                if (eachDateList.size() == 1) { //日期只有一筆資料
                    long workStart = converter.parse(belongDate + " 09:00").getTime();
                    long workEnd = converter.parse(belongDate + " 18:00").getTime();
                    long compareDate = eachDateList.get(0).getTime().getTime();

                    if (Math.abs(workStart - compareDate) <= Math.abs(workEnd - compareDate)) {
                        clockTime.setStartTime(eachDateList.get(0).getTime());
                        clockTime.setStartId(eachDateList.get(0).getId());
                    } else {
                        clockTime.setEndTime(eachDateList.get(0).getTime());
                        clockTime.setEndId(eachDateList.get(0).getId());
                    }

                    clockTime.setStatus(Constant.UNNORMAL); //異常
                } else {
                    clockTime.setStartTime(eachDateList.get(0).getTime());
                    clockTime.setStartId(eachDateList.get(0).getId());
                    clockTime.setEndTime(eachDateList.get(eachDateList.size() - 1).getTime());
                    clockTime.setEndId(eachDateList.get(eachDateList.size() - 1).getId());
                    //比對上班時間是否異常
                    long startTime = clockTime.getStartTime().getTime();
                    long endTime = clockTime.getEndTime().getTime();

                    double workingTime = leaveService.getWorkingHour(startTime, endTime);
                    List<EmployeeLeave> leaveList = employeeLeaveDAO.findByEmployeeIdAndLeaveDate(empNo, converter2.parse(belongDate));
                    for (EmployeeLeave employeeLeave : leaveList) {
                        long leaveStart = employeeLeave.getStartTime().getTime();
                        long leaveEnd = employeeLeave.getEndTime().getTime();
                        workingTime += leaveService.getWorkingHour(leaveStart, leaveEnd);

                        if (startTime < leaveStart && endTime > leaveEnd) { //請假時間在打卡區間裡
                            workingTime -= leaveService.getWorkingHour(leaveStart, leaveEnd);
                        } else if (startTime < leaveStart && endTime > leaveStart) { //請假時間重複，打卡時間較早
                            workingTime -= leaveService.getWorkingHour(leaveEnd, startTime);
                        } else if (leaveStart < startTime && leaveEnd > startTime) { //請假時間重複，打卡時間較晚
                            workingTime -= leaveService.getWorkingHour(startTime, leaveEnd);
                        }

                    }

                    if (workingTime >= 8) { //上班時間有八小時
                        clockTime.setStatus(Constant.NORMAL); //正常
                    } else {
                        clockTime.setStatus(Constant.UNNORMAL); //異常
                    }


                }
                clockTimeDAO.save(clockTime);
            }
        }

        return "success";
    }

    @PostMapping("/clockRaw")
    public ClockRaw createClockTime(int empNo, String time) throws ParseException {
        ClockRaw clockRaw = new ClockRaw(0, new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time).getTime()), empNo);
        return clockRawDAO.save(clockRaw);
    }

    @DeleteMapping("/clockRaw")
    public String deleteEmployee(int id) {
        clockRawDAO.deleteById(id);

        //將clockTime的相關資料作刪除
        List<ClockTime> clockTimeList = clockTimeDAO.findByStartId(id);
        for (ClockTime clockTime : clockTimeList) {
            clockTime.setStartTime(null);
            clockTime.setStartId(null);
            clockTime.setStatus(Constant.UNNORMAL);
            clockTimeDAO.save(clockTime);
        }

        clockTimeList = clockTimeDAO.findByEndId(id);
        for (ClockTime clockTime : clockTimeList) {
            clockTime.setEndTime(null);
            clockTime.setEndId(null);
            clockTime.setStatus(Constant.UNNORMAL);
            clockTimeDAO.save(clockTime);
        }
        return "{\"delete\":\"success!\"}";
    }
}
