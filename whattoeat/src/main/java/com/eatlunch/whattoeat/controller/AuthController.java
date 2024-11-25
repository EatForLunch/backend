package com.eatlunch.whattoeat.controller;

import com.eatlunch.whattoeat.dto.common.TokenInfo;
import com.eatlunch.whattoeat.dto.request.user.UserLoginRequest;
import com.eatlunch.whattoeat.dto.response.ApiResponse;
import com.eatlunch.whattoeat.dto.response.auth.AuthResponse;
import com.eatlunch.whattoeat.dto.response.user.LoginResponse;
import com.eatlunch.whattoeat.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody UserLoginRequest request) {
    AuthResponse authResponse = authService.login(request);
    LoginResponse loginResponse = LoginResponse.of(authResponse.getUser());

    return ResponseEntity.ok()
        .header("Authorization", "Bearer " + authResponse.getTokenInfo().getAccessToken())
        .body(ApiResponse.success(loginResponse, "로그인에 성공했습니다."));
  }

  @PostMapping("/refresh")
  public ResponseEntity<ApiResponse<Void>> refresh(@RequestHeader("Authorization") String refreshToken) {
    TokenInfo tokenInfo = authService.refreshToken(refreshToken);

    return ResponseEntity.ok()
        .header("Authorization", "Bearer " + tokenInfo.getAccessToken())
        .body(ApiResponse.success(null, "토큰이 갱신되었습니다."));
  }

  // Exception Handlers
  // 403
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException e) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ApiResponse.error("인증에 실패했습니다: " + e.getMessage()));
  }

  // 400
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(e.getMessage()));
  }

  // 500
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error("서버 오류가 발생했습니다."));
  }
}