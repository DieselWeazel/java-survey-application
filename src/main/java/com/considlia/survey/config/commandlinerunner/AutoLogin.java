package com.considlia.survey.config.commandlinerunner;

import com.considlia.survey.config.AutoLoginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
/**
 * {@code CommandLineRunner} used to optionally log a user in automatically.
 */
@Component
@Order(200)
class AutoLogin implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(AutoLogin.class);

  private final AutoLoginProperties properties;
  private final AuthenticationManager authenticationManager;

  @Autowired
  AutoLogin(final AutoLoginProperties properties,
      final AuthenticationManager authenticationManager) {
    this.properties = properties;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public void run(final String...args) {
    if (properties.isEnabled()) {
      LOGGER.info("Performing auto-login for user '{}'", properties.getUsername());
      SecurityContextHolder.getContext()
        .setAuthentication(
          authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
              properties.getUsername(),
              properties.getPassword())));
    } else {
      LOGGER.info("Auto-login disabled");
    }
  }
}
