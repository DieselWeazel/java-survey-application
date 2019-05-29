package com.considlia.survey.security;

import com.considlia.survey.model.User;
import com.considlia.survey.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService extends User {

  @Autowired private UserRepository userRepository;

  /**
   * Constructor for UserRepository
   *
   * @param userRepository inputs the database relation of Users.
   */
  public CustomUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * In order to retrieve User info as User object.
   *
   * @return currently logged in {@link User}
   */
  public User getUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = new User();
    // If Principal is of type User, getList the User.
    // Mandatory
    if (principal instanceof UserDetails) {
      String username = ((UserDetails) principal).getUsername();

      user = userRepository.findByUsername(username);
      return user;
    }
    return user;
  }
}
