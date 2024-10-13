package com.mck.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "UserInfo")
@Getter
@Setter
public class UserInfo extends AbstractEntity {

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

}
