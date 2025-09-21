package org.example.springbootinpractice.ch02.validation;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Curse {
    private long id;
    private String name;
    private String category;

    @Min(value = 1, message = "A curse should have a minimum of 1 rating") // 최솟값 지정해서 이값보다 크거나 같은지 검사
    @Max(value = 5, message = "A curse should have a maximum of 5 rating") // 최댓값을 지정해서 이 값보가 작거나 같은지 검사
    private int rating;

    @NotBlank // CharSequence 타입 필드에사용, 문자열이 null이 아니고, 앞뒤 공백문제를 제거한 후 문자열 길이가 0보다 크다는 것 검사
    @NotEmpty // CharSequence, Collection, Map 타입과 배열에 사용, null이 아니고 비어있지않음 검사
    @NotNull // 모든 타입에 사용할 수 있으며 null이 아님을 검사
    @Pattern(regexp = "", flags = Pattern.Flag.CASE_INSENSITIVE) // regexp로 지정한 정규 표현식을 준수하는지 검사, 정규표현식의 flag값도 사용가능
    @Size(min = 1, max = 50) // 개수의 최솟값, 최대값을 준수하는지 검사
    @Email // 문자열이 유효한 이메일 주소를 나타내는지 검사
    private String description;
}
