package com.tr.scheduleapi;

import org.springframework.boot.SpringApplication; // bootstrap launch : context - autoconfig - launch tomcat
import org.springframework.boot.autoconfigure.SpringBootApplication; // meta annotation : @Configuration + @EnableAutoConfiguration + @ComponentScan : com.tr.scheduleapi -> domain.. services .. etc.

@SpringBootApplication // 스캔 기준점
public class ScheduleApiApplication {
    public static void main(String[] args){ // args: 실행 시 전달된 CLI 인자(e.g., --server.port=8080~8081 etc) 전달. application.yml/.properties : Spring이 별도로 읽어 Environment에 병합.
        SpringApplication.run(ScheduleApiApplication.class, args); // app context -> autoconfig -> Bean 등록, 컨포넌트 스캔 -> 웹 서버 기동 -> Scan -> API
    }
}



// @SpringBootApplication(scanBasePackages = {"com.training.one", "com.demo.two"}) etc..