package org.example.springbootinpractice.ch02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseTrackerApplication implements CommandLineRunner {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(CourseTrackerApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner() {
//
//        return args -> {
////            logger.info("CustomProperties -> {}", customProperties);
//            logger.info("CommandLineRunner started with arguments: {}", args.length);
//        };

    /// /        return args -> {
    /// /            logger.info("CommandLineRunner started with arguments: {}", args.length);
    /// /            for (String a : args) {
    /// /                logger.info("Argument {} ",a);
    /// /            }
    /// /        };
//    }
    @Override
    public void run(String... args) throws Exception {
        logger.info("CommandLineRunner has executed");
    }
}
