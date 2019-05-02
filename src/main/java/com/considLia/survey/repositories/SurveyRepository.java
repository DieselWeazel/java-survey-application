package com.considLia.survey.repositories;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.considLia.survey.model.Survey;
import com.considLia.survey.model.TextQuestion;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

  Survey getSurveyBySurveyId(long surveyId);

  List<TextQuestion> getQuestionBySurveyId(long surveyId);
}
