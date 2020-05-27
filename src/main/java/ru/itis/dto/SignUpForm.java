package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {

    @Size(min = 2, message = "{Size.signUpDto.login.empty}")
    @Email(message = "{Email.signUpDto.login}")
    private String email;

//    @NotEmpty(message = "{NotEmpty.signUpDto.password}")
    @Size(min = 2, max = 30)
    private String password;
}
