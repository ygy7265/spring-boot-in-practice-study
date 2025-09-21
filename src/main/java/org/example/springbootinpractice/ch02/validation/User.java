package org.example.springbootinpractice.ch02.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class User {
    private String userName;

    @Password
    private String password;

}
