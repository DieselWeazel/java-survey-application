package com.considlia.survey;

import com.considlia.survey.config.AutoLoginProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AutoLoginProperties.class)
public class SurveyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SurveyApplication.class, args);
  }
}
