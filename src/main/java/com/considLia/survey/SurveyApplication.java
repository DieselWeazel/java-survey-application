package com.considLia.survey;

import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.considLia.survey.model.MultiQuestion;
import com.considLia.survey.model.MultiQuestionAlternative;
import com.considLia.survey.model.Survey;
import com.considLia.survey.model.TextQuestion;
import com.considLia.survey.repositories.SurveyRepository;

@SpringBootApplication
public class SurveyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SurveyApplication.class, args);
  }

  @Bean
  public CommandLineRunner initDb(SurveyRepository surveyRepository) {
    return args -> {

      Survey survey1 = new Survey();
      survey1.setSurveyTitle("Test");
      survey1.setCreator("Jens");

      MultiQuestion multi = new MultiQuestion();
      multi.setQuestionTitle("Test multi");
      multi.setQuestionType(1);

      MultiQuestionAlternative multiAlt = new MultiQuestionAlternative();
      multiAlt.setQuestionTitle("Testalternativ1");
      MultiQuestionAlternative multiAlt2 = new MultiQuestionAlternative();
      multiAlt2.setQuestionTitle("Testalternativ2");
      TextQuestion text = new TextQuestion();
      text.setQuestionTitle("Test textfråga");

      Set<MultiQuestionAlternative> alternativeList = new HashSet<>();
      alternativeList.add(multiAlt);
      alternativeList.add(multiAlt2);

      multi.setAlternativeList(alternativeList);

      Set<MultiQuestion> questionList = new HashSet<>();
      questionList.add(multi);
      Set<TextQuestion> textQuestionList = new HashSet<>();
      textQuestionList.add(text);

      survey1.setMultiQuestionList(questionList);
      survey1.setTextQuestionList(textQuestionList);

      surveyRepository.save(survey1);

    };
  }
}
