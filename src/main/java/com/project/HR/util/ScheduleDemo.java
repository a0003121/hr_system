package com.project.HR.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduleDemo {

//    @Scheduled(initialDelay = 180000, fixedRate = 5000) // initialDelay = 180000 表示延遲 180000 (3秒) 執行第一次任務, 然後每 5000ms(5 秒) 再次呼叫該方法
//    public void testInitialDelay() {
//        log.info("===initialDelay: 時間:");
//    }
}
