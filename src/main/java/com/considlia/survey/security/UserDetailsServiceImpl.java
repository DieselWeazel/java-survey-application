package com.considlia.survey.security;

import com.considlia.survey.model.User;
import com.considlia.survey.repositories.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    if (null == user) {
      throw new UsernameNotFoundException(username + " does not exist.");
    } else {
      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
          Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
  }
}
