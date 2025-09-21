package org.example.springbootinpractice.ch02.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// SOURCE 를 지정하면 소스코드 수준에서만 효력을 발휘하고 컴파일된 바이너리 결과물에는 포함되지않음
@Target({ElementType.METHOD, ElementType.FIELD}) // 애너테이션을 적용할 대상 타입
@Retention(RetentionPolicy.RUNTIME) // 언제까지 효력을 유지하고 살아남는지 * 런타임동안
@Constraint(validatedBy = PasswordRuleValidator.class) // validatedBy : 제약 사항이 구현된 클래스
public @interface Password {

    String message() default "Invalid password"; // 유효성 검증에 실패할 때 표시

    Class<?>[] groups() default {}; // 그룹을 지정하면 validation을 그룹별로 구분해서 적용 가능

    Class<? extends Payload>[] payload() default {}; // 밸리데이션 클라이언트가 사용하는 메타데이터 전달

}
