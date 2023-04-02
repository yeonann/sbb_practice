package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25, message = "ID는 3~25자로 입력하세요.")
    @NotBlank(message = "ID를 입력하세요.")
    private String username;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password1;
    @NotBlank(message = "비밀번호 확인을 입력하세요.")
    private String password2;
    @NotBlank(message = "이메일을 입력하세요.")
    @Email
    private String email;
}