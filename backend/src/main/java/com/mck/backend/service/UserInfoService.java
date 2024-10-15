package com.mck.backend.service;

import com.mck.backend.domain.UserInfo;
import com.mck.backend.exception.AlreadyExistsException;
import com.mck.backend.model.UserInfoDetails;
import com.mck.backend.repository.UserInfoRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {

  private final UserInfoRepository repository;

  private final PasswordEncoder encoder;

  public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder) {
    this.repository = repository;
    this.encoder = encoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserInfo> userDetail = repository.findByUsername(username);
    return userDetail.map(UserInfoDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
  }

  public String addUser(UserInfo userInfo) {
    Optional<UserInfo> userDetail = repository.findByUsername(userInfo.getUsername());
    if (userDetail.isPresent()) {
      throw new AlreadyExistsException("User already exists");
    }
    userInfo.setPassword(encoder.encode(userInfo.getPassword()));
    repository.save(userInfo);
    return "User Added Successfully";
  }

}
