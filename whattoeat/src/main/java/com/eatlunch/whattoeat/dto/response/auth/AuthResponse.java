package com.eatlunch.whattoeat.dto.response.auth;

import com.eatlunch.whattoeat.dto.common.TokenInfo;
import com.eatlunch.whattoeat.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
  private TokenInfo tokenInfo;
  private User user;  // 또는 필요한 사용자 정보만 담은 DTO
}
