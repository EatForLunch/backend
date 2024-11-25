package com.eatlunch.whattoeat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
  private final String status;  // SUCCESS, ERROR
  private final String message;
  private final T data;        // 실제 응답 데이터 (Generic 타입)

  // 성공 응답 생성을 위한 정적 팩토리 메서드
  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder()
        .status("SUCCESS")
        .message("성공적으로 처리되었습니다.")
        .data(data)
        .build();
  }

  // 성공 응답 생성 (메시지 지정)
  public static <T> ApiResponse<T> success(T data, String message) {
    return ApiResponse.<T>builder()
        .status("성공적인 응답!")
        .message(message)
        .data(data)
        .build();
  }

  // 에러 응답 생성
  public static <T> ApiResponse<T> error(String message) {
    return ApiResponse.<T>builder()
        .status("제대로 도달했지만.. 문제가 있는 응답!")
        .message(message)
        .build();
  }
}
