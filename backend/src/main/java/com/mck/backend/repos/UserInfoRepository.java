package com.mck.backend.repos;

import com.mck.backend.domain.UserInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

  Optional<UserInfo> findByUsername(String username);
}

