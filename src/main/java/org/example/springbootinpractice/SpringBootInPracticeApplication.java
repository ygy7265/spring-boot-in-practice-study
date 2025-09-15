package org.example.springbootinpractice;

import org.example.springbootinpractice.ch01.AppService;
import org.example.springbootinpractice.ch01.ApplicationStartingEventListener;
import org.example.springbootinpractice.ch01.DbConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

import java.util.Date;
@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringBootInPracticeApplication {
    private static final Logger log = LoggerFactory.getLogger(SpringBootInPracticeApplication.class);
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootInPracticeApplication.class, args);
        AppService appService = applicationContext.getBean(AppService.class);
        log.info("App Info -> {}", appService.getCustomProperties().toString());
        DbConfiguration dbConfiguration = applicationContext.getBean(DbConfiguration.class);
//        log.info("Db Connection Info -> {}", dbConfiguration.toString());
//        SpringApplication springApplication = new SpringApplication(SpringBootInPracticeApplication.class);
//        springApplication.addListeners(new ApplicationStartingEventListener());
//
//        springApplication.run(args);
//        SpringApplication.run(SpringBootInPracticeApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReadyEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("Application Ready Event generated at " + new Date(applicationReadyEvent.getTimestamp()));
    }

}
