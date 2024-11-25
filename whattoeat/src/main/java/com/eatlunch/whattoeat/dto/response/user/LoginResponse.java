package com.eatlunch.whattoeat.dto.response.user;

import com.eatlunch.whattoeat.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
  private String email;  // 사용자 식별자

  public static LoginResponse of(User user) {
    return LoginResponse.builder()
        .email(user.getEmail())
        .build();
  }
}