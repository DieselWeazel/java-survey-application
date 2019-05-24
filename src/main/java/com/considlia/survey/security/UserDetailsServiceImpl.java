package com.considlia.survey.security;

import com.considlia.survey.model.User;
import com.considlia.survey.repositories.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  /**
   * Constructs an implementation of {@link UserDetailsService}
   * @param userRepository our repository connection to database, consisting of
   * type {@link User}, for validating User existence.
   */
  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Loads {@link User} via Username of User, for use with roles and access.
   * Not to be confused with loading object of User, see {@link CustomUserService}
   * @param username if username exists within database or not.
   * @return if Username exists, returns instance of {@link UserDetails}
   * @throws UsernameNotFoundException if Username doesn't exist within Database.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (null == user) {
      throw new UsernameNotFoundException("No user found // Create Something else here!");
    } else {
      return new org.springframework.security.core.userdetails.User(
          user.getUsername(),
          user.getPassword(),
          Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
  }
}
