package com.eatlunch.whattoeat.utils;
import com.github.f4b6a3.ulid.UlidCreator;

public class UlidGenerator {
  public static String generateUserId() {

    // ULID 생성 (타임스탬프 + 랜덤값으로 구성된 26자리 문자열)
    String ulid = UlidCreator.getUlid().toString().toLowerCase();
    return "user_" + ulid;
  }
}
