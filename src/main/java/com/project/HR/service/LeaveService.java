package com.project.HR.service;

import com.project.HR.dao.CalendarDAO;
import com.project.HR.vo.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class LeaveService {
    @Autowired
    CalendarDAO calendarDAO;

    public double getWorkingHour(Long startTime, Long endTime){
        // 定義
        float startBreak = 12f; // 休息開始
        float endBreak = 13f; // 休息結束
        float workHourStart = 9; // 工作開始
        float workHourEnd = 18; // 工作結束
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        java.util.Calendar startCal = new GregorianCalendar();
        startCal.setTimeInMillis(startTime);
        java.util.Calendar endCal = new GregorianCalendar();
        endCal.setTimeInMillis(endTime);
        float leaveStart = startCal.get(java.util.Calendar.HOUR_OF_DAY)
                + (startCal.get(java.util.Calendar.MINUTE) / 60f);
        float leaveEnd = endCal.get(java.util.Calendar.HOUR_OF_DAY) + (endCal.get(java.util.Calendar.MINUTE) / 60f);
        float totalHours = 0;
        // 開始時間早於上班時間
        leaveStart = leaveStart < workHourStart ? workHourStart : leaveStart;
        // 結束時間晚於下班時間
        leaveEnd = leaveEnd > workHourEnd ? workHourEnd : leaveEnd;
        // 開始時間在午休
        leaveStart = leaveStart > startBreak && leaveStart < endBreak ? startBreak : leaveStart;
        // 結束時間在午休
        leaveEnd = leaveEnd > startBreak && leaveEnd < endBreak ? endBreak : leaveEnd;

        List<Calendar> calendars = calendarDAO.findByDateBetween(startDate, endDate, Sort.by("date").ascending());

        // 如果請假日是休假
        if (calendars.size() == 0 || (calendars.size() == 1 && calendars.get(0).getType() == 2)) {
            return 0;
        }

        // 開始與結束是同一天
        if (calendars.size() == 1) {

            // 請假時間不是上班時間
            if (leaveStart > workHourEnd || leaveEnd < workHourStart) {
                return 0;
            }

            // 上班和下班時間都早於午休開始
            if (leaveEnd <= startBreak)
                totalHours = leaveEnd - leaveStart;
                // 上班和下班時間都晚於午休結束
            else if (leaveStart >= endBreak)
                totalHours = leaveEnd - leaveStart;
                // 一早一晚
            else
                totalHours = leaveEnd - leaveStart - (endBreak - startBreak);

            return Math.ceil(totalHours * 2) / 2;
        }

        // 開始與結束不是同一天
        for (Calendar calendar : calendars) {
            if (calendar.getType() == 0) { // 工作日

                java.util.Calendar checkCal = new GregorianCalendar();
                checkCal.setTime(calendar.getDate());

                if (startCal.get(java.util.Calendar.YEAR) == checkCal.get(java.util.Calendar.YEAR)
                        && startCal.get(java.util.Calendar.MONTH) == checkCal.get(java.util.Calendar.MONTH)
                        && startCal.get(java.util.Calendar.DATE) == checkCal.get(java.util.Calendar.DATE)) {

                    if (leaveStart > workHourEnd)
                        leaveStart = workHourEnd;
                    // 請假開始時間早於午休
                    if (leaveStart <= startBreak) {
                        totalHours += workHourEnd - leaveStart - (endBreak - startBreak);
                    } else {// 請假開始時間晚於午休
                        totalHours += workHourEnd - leaveStart;
                    }

                } else if (endCal.get(java.util.Calendar.YEAR) == checkCal.get(java.util.Calendar.YEAR)
                        && endCal.get(java.util.Calendar.MONTH) == checkCal.get(java.util.Calendar.MONTH)
                        && endCal.get(java.util.Calendar.DATE) == checkCal.get(java.util.Calendar.DATE)) {

                    if (leaveEnd < workHourStart)
                        leaveEnd = workHourStart;
                    // 請假結束時間早於午休
                    if (leaveEnd <= startBreak) {
                        totalHours += leaveEnd - workHourStart;
                    } else { // 請假結束時間晚於午休
                        totalHours += leaveEnd - workHourStart - (endBreak - startBreak);
                    }
                } else {
                    totalHours += 8;
                }
            }
        }
        return Math.ceil(totalHours * 2) / 2;
    }
}
