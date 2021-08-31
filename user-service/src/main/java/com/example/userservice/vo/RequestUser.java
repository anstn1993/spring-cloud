package com.example.userservice.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUser {
  @NotNull(message = "Email can't be null")
  @Size(min = 2, message = "Email must be equal or greater than two characters")
  @Email
  private String email;

  @NotNull(message = "Name can't be null")
  @Size(min = 2, message = "Name must be equal or greater than two characters")
  private String name;

  @NotNull(message = "Password can't be null")
  @Size(min = 8, message = "Password must not be equal or greater than eight characters")
  private String pwd;
}
