// MemberController.java
package com.eatlunch.whattoeat.controller;

import com.eatlunch.whattoeat.dto.request.user.UserSignupRequest;
import com.eatlunch.whattoeat.dto.response.ApiResponse;
import com.eatlunch.whattoeat.entity.User;
import com.eatlunch.whattoeat.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<User> signup(@Valid @RequestBody UserSignupRequest request) {
    User user = userService.signup(request);
    return ResponseEntity.ok(user);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error(e.getMessage()));
  }
}
