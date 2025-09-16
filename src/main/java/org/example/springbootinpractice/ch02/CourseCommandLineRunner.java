package org.example.springbootinpractice.ch02;

import lombok.RequiredArgsConstructor;
import org.example.springbootinpractice.ch01.CustomProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseCommandLineRunner implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(CourseCommandLineRunner.class);
    private final CustomProperties customProperties;
    @Override
    public void run(String... args) throws Exception {
        logger.info("CustomProperties: {}", customProperties);
    }
}
