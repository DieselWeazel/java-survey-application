package com.considlia.survey.config.commandlinerunner;

import com.considlia.survey.model.User;
import com.considlia.survey.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(100)
class DatabaseInitializer implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  DatabaseInitializer(final UserRepository userRepository,
      final PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Order(100)
  public void run(final String...args) {
    LOGGER.info("Initializing database...");

    User adminUser = new User();
    adminUser.setFirstName("admin");
    adminUser.setLastName("admin");
    adminUser.setEmail("admin@gmail.com");
    adminUser.setRole("ADMIN");
    adminUser.setUsername("admin");
    adminUser.setPassword(passwordEncoder.encode("admin"));
    userRepository.save(adminUser);

    User user = new User();
    user.setFirstName("noob");
    user.setLastName("scrub");
    user.setEmail("newbie@gmail.com");
    user.setRole("USER");
    user.setUsername("user");
    user.setPassword(passwordEncoder.encode("user"));
    userRepository.save(user);

    LOGGER.info("Database initialized");
  }
}
