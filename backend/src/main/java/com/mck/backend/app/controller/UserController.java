package com.mck.backend.app.controller;

import com.mck.backend.app.request.AuthRequest;
import com.mck.backend.domain.UserInfo;
import com.mck.backend.service.UserInfoService;
import com.mck.backend.service.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

  private final UserInfoService service;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  UserController(UserInfoService service, JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.service = service;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/register")
  public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
    String response = service.addUser(userInfo);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/generateToken")
  public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
            authRequest.getPassword()));
    if (authentication.isAuthenticated()) {
      String token = jwtService.generateToken(authRequest.getUsername());
      return ResponseEntity.ok(token);
    } else {
      throw new UsernameNotFoundException("Invalid user request!");
    }
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello World!";
  }

}
