package org.example.springbootinpractice;

import org.example.springbootinpractice.ch01.ApplicationStartingEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Date;

@SpringBootApplication
public class SpringBootInPracticeApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootInPracticeApplication.class);
        springApplication.addListeners(new ApplicationStartingEventListener());
        springApplication.run(args);
//        SpringApplication.run(SpringBootInPracticeApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReadyEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("Application Ready Event generated at " + new Date(applicationReadyEvent.getTimestamp()));
    }

}
