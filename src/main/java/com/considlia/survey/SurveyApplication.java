package com.considlia.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.repositories.UserRepository;

/*
 * no users yet, login view showing however!
 * 
 * Add a user and try the functionality
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.considlia.survey")
public class SurveyApplication {

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Starts the application.
   * 
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(SurveyApplication.class, args);
  }


  /**
   * Used to create users in system in code as a tool for development.
   * 
   * Method should be removed before publishing application.
   * 
   * @param surveyRepository
   * @param userRepository
   * @return
   */
  @Bean
  public CommandLineRunner initDb(SurveyRepository surveyRepository,
      UserRepository userRepository) {
    return args -> {
      // User adminUser = new User();
      // adminUser.setFirstName("admin");
      // adminUser.setLastName("admin");
      // adminUser.setEmail("admin@gmail.com");
      // adminUser.setRole("ADMIN");
      // adminUser.setUsername("admin");
      // adminUser.setPassword(passwordEncoder.encode("admin"));
      //
      // userRepository.save(adminUser);
      //
      // User user = new User();
      // user.setFirstName("noob");
      // user.setLastName("scrub");
      // user.setEmail("newbie@gmail.com");
      // user.setRole("USER");
      // user.setUsername("user");
      // user.setPassword(passwordEncoder.encode("user"));
      //
      // userRepository.save(user);
    };
  }
}
