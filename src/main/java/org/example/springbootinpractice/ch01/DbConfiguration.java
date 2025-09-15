package org.example.springbootinpractice.ch01;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource("classpath:dbConfig.properties")
//@PropertySource("classpath:redis.properties") // 없으면 에러 이런식으로 2개로도 가져올수있음.
public class DbConfiguration {
    Logger logger = LoggerFactory.getLogger(DbConfiguration.class);
    @Autowired
    private Environment environment;
    @PostConstruct
    public void init(){
        logger.info("DB configuration has been initialized -> {} ", toString());
    }
    @Override
    public String toString() {
        return "DbConfiguration{" +
                "user=" + environment.getProperty("user")+
                "password=" + environment.getProperty("password")+
                '}';
    }
}
