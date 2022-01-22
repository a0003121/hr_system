package com.project.HR.controller;

import com.project.HR.dao.CalendarDAO;
import com.project.HR.vo.Calendar;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
//@EnableJpaAuditing//啟用審計(Auditing)
public class JSONController_Calendar {
    @Autowired
    CalendarDAO calendarDAO;

    ////////// *行事曆*///////////
    @GetMapping("/calendars")
    public String getCalendarbyYearAndType(String year) throws ParseException {
        SimpleDateFormat converter = new SimpleDateFormat("yyyyMMdd");
        Date dateStart = converter.parse(year + "0101");
        Date dateEnd = converter.parse(year + "1231");
        List<Calendar> calendars = calendarDAO.findByDateBetween(new java.sql.Date(dateStart.getTime()),
                new java.sql.Date(dateEnd.getTime()));

        JSONArray jsonArray = new JSONArray();
        for (Calendar calendar : calendars) {
            JSONObject obj = new JSONObject();
            obj.put("id", calendar.getId());
            obj.put("title", calendar.getNote());
            obj.put("start", calendar.getDate());
            obj.put("type", calendar.getType());
            if (calendar.getType() == 0) {
                obj.put("color", "#3f51b5");
            } else {
                obj.put("color", "#f8610b");
            }
            jsonArray.put(obj);
        }

        return jsonArray.toString();
    }

    @PutMapping("/calendar")
    public Calendar updateCalendar(String id, int type, String note) throws ParseException {
        Calendar calendar = calendarDAO.findById(Integer.parseInt(id)).get();
        System.out.println(type);
        calendar.setType(type);
        calendar.setNote(note);
        return calendarDAO.save(calendar);
    }

}
