package com.eatlunch.whattoeat.dto.response.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponse {
  private final String email;
  private final String name;

  public static SignupResponse of(String email, String name) {
    return SignupResponse.builder()
        .email(email)
        .name(name)
        .build();
  }
}
