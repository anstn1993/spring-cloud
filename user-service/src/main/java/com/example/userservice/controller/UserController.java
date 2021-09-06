package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class UserController {

  private final Greeting greeting;
  private final UserService userService;
  private final ModelMapper modelMapper;
  private final Environment env;

  public UserController(Greeting greeting, UserService userService, ModelMapper modelMapper,
      Environment env) {
    this.greeting = greeting;
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.env = env;
  }

  @GetMapping("/health_check")
  public String status() {
    return String.format("It's Working in User Service"
        + ", port(local.server.port)=" + env.getProperty("local.server.port")
        + ", port(server.port)=" + env.getProperty("server.port")
        + ", token secret=" + env.getProperty("token.secret")
        + ", token expiration time=" + env.getProperty("token.expiration_time")
    );
  }

  @GetMapping("/welcome")
  public String welcome() {
    return greeting.getMessage();
  }

  @PostMapping("/users")
  public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser) {
    UserDto userDto = modelMapper.map(requestUser, UserDto.class);
    userService.createUser(userDto);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(modelMapper.map(userDto, ResponseUser.class));
  }

  @GetMapping("/users")
  public ResponseEntity<List<ResponseUser>> getUsers() {
    Iterable<UserEntity> users = userService.getUserByAll();
    List<ResponseUser> result = new ArrayList<>();
    for (UserEntity user : users) {
      result.add(modelMapper.map(user, ResponseUser.class));
    }
    return ResponseEntity.ok(result);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<ResponseUser> getUser(@PathVariable String userId) {
    UserDto user = userService.getUserByUserId(userId);
    return ResponseEntity.ok(modelMapper.map(user, ResponseUser.class));
  }
}
