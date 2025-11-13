package com.tr.scheduleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tr.scheduleapi") // 진입
public class ScheduleApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleApiApplication.class, args);
    }
}
