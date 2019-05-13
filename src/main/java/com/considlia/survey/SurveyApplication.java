package com.considlia.survey;

import com.considlia.survey.model.User;
import com.considlia.survey.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.considlia.survey.repositories.SurveyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
no users yet, login view showing however!

Add a user and try the functionality
 */
@SpringBootApplication
public class SurveyApplication {


  @Autowired
  private PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(SurveyApplication.class, args);
  }

  @Bean
  public CommandLineRunner initDb(SurveyRepository surveyRepository, UserRepository userRepository) {
    return args -> {
      // Survey survey1 = new Survey();
      // survey1.setTitle("Test");
      // survey1.setCreator("Jens");
      // survey1.setDate(LocalDate.now());
      // //
      // MultiQuestion multi = new MultiQuestion();
      // multi.setTitle("aTest multi");
      // multi.setQuestionType(1);
      //
      // MultiQuestionAlternative multiAlt = new MultiQuestionAlternative();
      // multiAlt.setTitle("Första");
      // multiAlt.setPosition(1);
      // MultiQuestionAlternative multiAlt2 = new MultiQuestionAlternative();
      // multiAlt2.setTitle("Andra");
      // multiAlt2.setPosition(2);
      // MultiQuestionAlternative multiAlt3 = new MultiQuestionAlternative();
      // multiAlt3.setTitle("Tredje");
      // multiAlt3.setPosition(3);
      // MultiQuestionAlternative multiAlt4 = new MultiQuestionAlternative();
      // multiAlt4.setTitle("Fjärde");
      // multiAlt4.setPosition(4);
      //
      // TextQuestion text = new TextQuestion();
      // text.setTitle("bTest textfråga");
      //
      // Set<MultiQuestionAlternative> alternativeList = new HashSet<>();
      // alternativeList.add(multiAlt);
      // alternativeList.add(multiAlt2);
      // alternativeList.add(multiAlt3);
      // alternativeList.add(multiAlt4);
      //
      // multi.setAlternatives(alternativeList);
      //
      // MultiQuestion multi2 = new MultiQuestion();
      // multi2.setTitle("cTest multi // one response only");
      // multi2.setQuestionType(0);
      //
      // MultiQuestionAlternative singleAlt = new MultiQuestionAlternative();
      // singleAlt.setTitle("AAAAAAA");
      // singleAlt.setPosition(1);
      // MultiQuestionAlternative singleAlt2 = new MultiQuestionAlternative();
      // singleAlt2.setTitle("BBBBB");
      // singleAlt2.setPosition(2);
      // MultiQuestionAlternative singleAlt3 = new MultiQuestionAlternative();
      // singleAlt3.setTitle("CCCCCCC");
      // singleAlt3.setPosition(3);
      // MultiQuestionAlternative singleAlt4 = new MultiQuestionAlternative();
      // singleAlt4.setTitle("DDDDDDD");
      // singleAlt4.setPosition(4);
      //
      // Set<MultiQuestionAlternative> alternativeList2 = new HashSet<>();
      // alternativeList2.add(singleAlt);
      // alternativeList2.add(singleAlt2);
      // alternativeList2.add(singleAlt3);
      // alternativeList2.add(singleAlt4);
      //
      // multi2.setAlternatives(alternativeList2);
      //
      // text.setPosition(1);
      // multi.setPosition(2);
      // multi2.setPosition(3);
      //
      // Set<Question> questionList = new HashSet<>();
      // questionList.add(multi);
      // questionList.add(text);
      // questionList.add(multi2);
      //
      // survey1.setQuestions(questionList);
      //
      // surveyRepository.save(survey1);
      //
      // Survey survey2 = new Survey();
      // survey2.setTitle("Test");
      // survey2.setCreator("Jontes Test");
      // survey2.setDate(LocalDate.now());
      //
      // Question jonteQuestion1 = new TextQuestion();
      // jonteQuestion1.setTitle("FRÅGA 1");
      // jonteQuestion1.setPosition(1);
      //
      // Question jonteQuestion2 = new TextQuestion();
      // jonteQuestion2.setTitle("FRÅGA 2");
      // jonteQuestion2.setPosition(2);
      //
      // Set<Question> questionListJonte = new HashSet<>();
      // questionListJonte.add(jonteQuestion1);
      // questionListJonte.add(jonteQuestion2);
      //
      // survey2.setQuestions(questionListJonte);
      //
      // surveyRepository.save(survey2);


      User user = new User();
      user.setFirstName("admin");
      user.setLastName("admin");
      user.setEmail("jonathan@gmail.com");
      user.setRole("ADMIN");
      user.setUsername("admin");
      user.setPassword(passwordEncoder.encode("admin"));

      userRepository.save(user);

      User noob = new User();
      noob.setFirstName("noob");
      noob.setLastName("scrub");
      noob.setEmail("newbie@gmail.com");
      noob.setRole("USER");
      noob.setUsername("user");
      noob.setPassword(passwordEncoder.encode("user"));

      userRepository.save(noob);

      User august = new User();
      august.setFirstName("noob");
      august.setLastName("scrub");
      august.setEmail("augustmail@augusthemsida.se");
      august.setRole("USER");
      august.setUsername("august");
      august.setPassword(passwordEncoder.encode("user"));

      userRepository.save(august);
    };
  }
}
