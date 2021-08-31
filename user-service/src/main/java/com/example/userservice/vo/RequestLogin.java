package com.example.userservice.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {
  @NotNull(message = "Email can't be null")
  @Size(min = 2, message = "Email can not be less than two characters")
  @Email
  private String email;

  @NotNull(message = "Password can't be null")
  @Size(min = 8, message = "password must be equal or greater than eight characters")
  private String password;
}
