package com.eatlunch.whattoeat.repository;

import com.eatlunch.whattoeat.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
  // 회원 ID로 리프레시 토큰 조회
  Optional<RefreshToken> findByKey(String key);

  // 리프레시 토큰 값으로 조회
  Optional<RefreshToken> findByValue(String value);

  // 만료된 토큰 삭제
  @Modifying
  @Query("DELETE FROM RefreshToken r WHERE r.expiresAt < :now")
  void deleteAllExpiredTokens(@Param("now") LocalDateTime now);

  // 특정 회원의 토큰 존재 여부 확인
  boolean existsByKey(String key);

  // 특정 회원의 모든 리프레시 토큰 삭제 (로그아웃 등에 사용)
  @Modifying
  @Query("DELETE FROM RefreshToken r WHERE r.key = :key")
  void deleteByKey(@Param("key") String key);
}
