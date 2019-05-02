package com.considlia.survey;

import com.considlia.survey.repositories.SurveyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SurveyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SurveyApplication.class, args);
  }

  @Bean
  public CommandLineRunner initDb(SurveyRepository surveyRepository) {
    return args -> {

//       Survey survey1 = new Survey();
//       survey1.setSurveyTitle("Test");
//       survey1.setCreator("Jens");
//       survey1.setDate(LocalDate.now());
//       //
//       MultiQuestion multi = new MultiQuestion();
//       multi.setQuestionTitle("aTest multi");
//       multi.setQuestionType(1);
//
//       MultiQuestionAlternative multiAlt = new MultiQuestionAlternative();
//       multiAlt.setAlternativeTitle("Första");
//       multiAlt.setPosition(1);
//       MultiQuestionAlternative multiAlt2 = new MultiQuestionAlternative();
//       multiAlt2.setAlternativeTitle("Andra");
//       multiAlt2.setPosition(2);
//       MultiQuestionAlternative multiAlt3 = new MultiQuestionAlternative();
//       multiAlt3.setAlternativeTitle("Tredje");
//       multiAlt3.setPosition(3);
//       MultiQuestionAlternative multiAlt4 = new MultiQuestionAlternative();
//        multiAlt4.setAlternativeTitle("Fjärde");
//        multiAlt4.setPosition(4);
//
//
//       TextQuestion text = new TextQuestion();
//       text.setQuestionTitle("bTest textfråga");
//
//       Set<MultiQuestionAlternative> alternativeList = new HashSet<>();
//       alternativeList.add(multiAlt);
//       alternativeList.add(multiAlt2);
//       alternativeList.add(multiAlt3);
//       alternativeList.add(multiAlt4);
//
//       multi.setAlternativeList(alternativeList);
//
//       MultiQuestion multi2 = new MultiQuestion();
//       multi2.setQuestionTitle("cTest multi // one response only");
//       multi2.setQuestionType(0);
//
//        MultiQuestionAlternative singleAlt = new MultiQuestionAlternative();
//        singleAlt.setAlternativeTitle("AAAAAAA");
//        singleAlt.setPosition(1);
//        MultiQuestionAlternative singleAlt2 = new MultiQuestionAlternative();
//        singleAlt2.setAlternativeTitle("BBBBB");
//        singleAlt2.setPosition(2);
//        MultiQuestionAlternative singleAlt3 = new MultiQuestionAlternative();
//        singleAlt3.setAlternativeTitle("CCCCCCC");
//        singleAlt3.setPosition(3);
//        MultiQuestionAlternative singleAlt4 = new MultiQuestionAlternative();
//        singleAlt4.setAlternativeTitle("DDDDDDD");
//        singleAlt4.setPosition(4);
//
//        Set<MultiQuestionAlternative> alternativeList2 = new HashSet<>();
//        alternativeList2.add(singleAlt);
//        alternativeList2.add(singleAlt2);
//        alternativeList2.add(singleAlt3);
//        alternativeList2.add(singleAlt4);
//
//        multi2.setAlternativeList(alternativeList2);
//
//        text.setPosition(1);
//        multi.setPosition(2);
//        multi2.setPosition(3);
//
//        Set<Question> questionList = new HashSet<>();
//       questionList.add(multi);
//       questionList.add(text);
//       questionList.add(multi2);
//
//       survey1.setQuestionList(questionList);
//
//       surveyRepository.save(survey1);
//
//        Survey survey2 = new Survey();
//        survey2.setSurveyTitle("Test");
//        survey2.setCreator("Jontes Test");
//        survey2.setDate(LocalDate.now());
//
//        Question jonteQuestion1 = new TextQuestion();
//        jonteQuestion1.setQuestionTitle("FRÅGA 1");
//        jonteQuestion1.setPosition(1);
//
//        Question jonteQuestion2 = new TextQuestion();
//        jonteQuestion2.setQuestionTitle("FRÅGA 2");
//        jonteQuestion2.setPosition(2);
//
//        Set<Question> questionListJonte = new HashSet<>();
//        questionListJonte.add(jonteQuestion1);
//        questionListJonte.add(jonteQuestion2);
//
//        survey2.setQuestionList(questionListJonte);
//
//        surveyRepository.save(survey2);

    };
  }
}
