package com.eatlunch.whattoeat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@NoArgsConstructor
@Getter
public class RefreshToken {
  @Id
  @Column(name = "rt_key")
  private String key;

  @Column(name = "rt_value")
  private String value;

  @Column(name = "expires_at")
  private LocalDateTime expiresAt;

  @Builder
  public RefreshToken(String key, String value, LocalDateTime expiresAt) {
    this.key = key;
    this.value = value;
    this.expiresAt = expiresAt;
  }

  public void updateValue(String token, LocalDateTime expiresAt) {
    this.value = token;
    this.expiresAt = expiresAt;
  }
}