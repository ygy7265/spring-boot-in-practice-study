package org.example.springbootinpractice.ch02.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class PasswordRuleValidator implements ConstraintValidator<Password, String> {
    Logger log = LoggerFactory.getLogger(PasswordRuleValidator.class);
    // PasswordValidator 관련 설정 상수

    /**
     * 최소 복잡성 규칙 수
     * CharacterCharacteristicsRule에서 설정한 4개의 조건(대문자, 소문자, 숫자, 특수문자) 중
     * 최소 몇 개를 만족해야 하는지 정의
     */
    private static final int MIN_COMPLEX_RULES = 4;

    /**
     * 연속 반복 문자 최대 허용 개수
     * 같은 문자가 연속으로 MAX_REPETITIVE_CHARS번 이상 반복되면 위반 처리
     */
    private static final int MAX_REPETITIVE_CHARS = 3;

    /**
     * 최소 특수문자 개수
     * Password에 반드시 포함되어야 하는 특수문자 개수
     */
    private static final int MIN_SPECIAL_CASE_CHARS = 1;

    /**
     * 최소 대문자 개수
     * Password에 반드시 포함되어야 하는 대문자(A~Z) 개수
     */
    private static final int MIN_UPPER_CASE_CHARS = 1;

    /**
     * 최소 소문자 개수
     * Password에 반드시 포함되어야 하는 소문자(a~z) 개수
     */
    private static final int MIN_LOWER_CASE_CHARS = 1;

    /**
     * 최소 숫자 개수
     * Password에 반드시 포함되어야 하는 숫자(0~9) 개수
     */
    private static final int MIN_DIGIT_CASE_CHARS = 1;
    private static final Map<String, String> MESSAGE_MAP = Map.of(
            "INSUFFICIENT_SPECIAL", "특수문자를 최소 {0}개 포함해야 합니다.",
            "INSUFFICIENT_UPPERCASE", "대문자를 최소 {0}개 포함해야 합니다.",
            "INSUFFICIENT_LOWERCASE", "소문자를 최소 {0}개 포함해야 합니다.",
            "INSUFFICIENT_DIGIT", "숫자를 최소 {0}개 포함해야 합니다.",
            "ILLEGAL_MATCH", "동일 문자가 연속으로 {0}번 이상 반복될 수 없습니다.",
            "TOO_SHORT", "비밀번호는 최소 {0}자 이상이어야 합니다.",
            "TOO_LONG", "비밀번호는 최대 {0}자 이하이어야 합니다.",
            "INSUFFICIENT_CHARACTERISTICS", "비밀번호는 대문자, 소문자, 숫자, 특수문자를 모두 포함해야 합니다."
    );
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
//        PasswordPolicy policy = policyService.getPolicy();

        List<Rule> passwordRules = new ArrayList<Rule>();
        passwordRules.add(new LengthRule(8, 30));
        CharacterCharacteristicsRule characterCharacteristicsRule = new CharacterCharacteristicsRule(MIN_COMPLEX_RULES,
                new CharacterRule(EnglishCharacterData.UpperCase, MIN_UPPER_CASE_CHARS),
                new CharacterRule(EnglishCharacterData.LowerCase,MIN_LOWER_CASE_CHARS),
                new CharacterRule(EnglishCharacterData.Special,MIN_SPECIAL_CASE_CHARS),
                new CharacterRule(EnglishCharacterData.Digit,MIN_DIGIT_CASE_CHARS));

        passwordRules.add(characterCharacteristicsRule);
        passwordRules.add(new RepeatCharacterRegexRule(MAX_REPETITIVE_CHARS));
        PasswordValidator passwordValidator = new PasswordValidator(passwordRules);
        constraintValidatorContext.disableDefaultConstraintViolation();

        PasswordData passwordData = new PasswordData(password);
        RuleResult result = passwordValidator.validate(passwordData);
        if (result.isValid()) return true;


        if (!result.isValid()) {
            for (RuleResultDetail detail : result.getDetails()) {
                String code = detail.getErrorCode(); // INSUFFICIENT_SPECIAL 등
                log.info("Code -> {}", code);
                Object[] values = detail.getValues(); // 가변값 배열
                String template = MESSAGE_MAP.getOrDefault(code, "비밀번호 규칙을 준수해주세요.");

                // 가변값 치환
                String message = MessageFormat.format(template, values);
                constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode("password")
                        .addConstraintViolation();
            }
        }
        passwordValidator.getMessages(result);
        return false;
    }

    // Passay 메시지에서 코드 추출 (예: INSUFFICIENT_SPECIAL)
    private String extractCode(String msg) {
        int idx = msg.indexOf(':');
        if (idx > 0) return msg.substring(0, idx);
        return msg;
    }
}
