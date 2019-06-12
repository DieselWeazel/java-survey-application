package com.considlia.survey;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
    };
  }
}
