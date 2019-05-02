package com.considlia.survey;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.considlia.survey.repositories.SurveyRepository;

@SpringBootApplication
public class SurveyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SurveyApplication.class, args);
  }

  @Bean
  public CommandLineRunner initDb(SurveyRepository surveyRepository) {
    return args -> {

      // Survey survey1 = new Survey();
      // survey1.setSurveyTitle("Test");
      // survey1.setCreator("Jens");
      // survey1.setDate(LocalDate.now());
      // //
      // MultiQuestion multi = new MultiQuestion();
      // multi.setQuestionTitle("Test multi");
      // multi.setQuestionType(1);
      //
      // MultiQuestionAlternative multiAlt = new MultiQuestionAlternative();
      // multiAlt.setAlternativeTitle("Testalternativ1");
      // MultiQuestionAlternative multiAlt2 = new MultiQuestionAlternative();
      // multiAlt2.setAlternativeTitle("Testalternativ2");
      // TextQuestion text = new TextQuestion();
      // text.setQuestionTitle("Test textfråga");
      //
      // Set<MultiQuestionAlternative> alternativeList = new HashSet<>();
      // alternativeList.add(multiAlt);
      // alternativeList.add(multiAlt2);
      //
      // multi.setAlternativeList(alternativeList);
      //
      // Set<Question> questionList = new HashSet<>();
      // questionList.add(multi);
      // questionList.add(text);
      //
      // survey1.setQuestionList(questionList);
      //
      // surveyRepository.save(survey1);

    };
  }
}
