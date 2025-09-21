package org.example.springbootinpractice.ch02;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.example.springbootinpractice.ch01.CustomProperties;
import org.example.springbootinpractice.ch02.validation.Curse;
import org.example.springbootinpractice.ch02.validation.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CourseCommandLineRunner implements CommandLineRunner {
    Logger log = LoggerFactory.getLogger(CourseCommandLineRunner.class);
    private final CustomProperties customProperties;
    @Override
    public void run(String... args) throws Exception {
        log.info("CustomProperties: {}", customProperties);
        pojoValidator();
    }
    private void springValidator() {
        Curse curse = new Curse();
        curse.setId(1L);
        curse.setRating(0);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<Curse>> violations = validator.validate(curse);

        violations.forEach(courseConstraintViolation -> {
            log.error("A constraint violation has occurred. Violation details : [{}]", courseConstraintViolation.getMessage());
        });
    }
    private void pojoValidator() {
        User user1 = new User("kim01", "kim"); // 특수문자 위반
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user1);
        log.error("Password for use1 do not adhere to the password policy");
        for (ConstraintViolation<User> constraintViolation : violations) {
            log.error("violation details: [{}]", constraintViolation.getMessage());
        }
        User user2 = new User("kim02", "Kim7265!@!@"); // 성공
        violations = validator.validate(user2);
        if (violations.isEmpty()) {
            log.info("Password for user2 adhere to the password policy");
        }

        User user3 = new User("kim03", "Kim7265!@!@gggggg"); // 동일 문자열 위반
        violations = validator.validate(user3);
        violations.forEach(courseConstraintViolation -> {
            log.error("Password for use3 violates maximum repetitive rule [{}]", courseConstraintViolation.getMessage());
        });

        User user4 = new User("kim04", "Kim7265g1234"); // 특수문자, 동일 문자열 위반
        violations = validator.validate(user4);
        log.error("Password for use4 violates special character rule");
        violations.forEach(v -> {
            log.error("Property: {}", v.getPropertyPath());  // 어떤 필드(User.password 등)
            log.error("Invalid value: {}", v.getInvalidValue()); // 실제 입력 값
            log.error("Message: {}", v.getMessage()); // 어노테이션에 정의된 에러 메시지
            log.error("Constraint: {}", v.getConstraintDescriptor().getAnnotation()); // 위반한 제약 어노테이션
        });
    }
}
