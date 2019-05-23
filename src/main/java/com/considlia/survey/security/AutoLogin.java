package com.considlia.survey.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AutoLogin {

  private static final Logger LOGGER = LoggerFactory.getLogger(AutoLogin.class);

  @Autowired
  public AutoLogin(
      final AutoLoginProperties properties, final AuthenticationManager authenticationManager) {
    if (properties.isEnabled()) {
      LOGGER.info("Performing auto-login for user '{}'", properties.getUsername());
      SecurityContextHolder.getContext()
          .setAuthentication(
              authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                      properties.getUsername(), properties.getPassword())));
    } else {
      LOGGER.info("Auto-login disabled");
    }
  }
}
