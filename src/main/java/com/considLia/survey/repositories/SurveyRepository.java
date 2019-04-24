package com.considLia.survey.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.considLia.survey.model.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
  Survey getSurveyBySurveyId(long surveyId);
}
