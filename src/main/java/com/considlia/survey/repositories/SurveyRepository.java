package com.considlia.survey.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.considlia.survey.model.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
  Survey getSurveyBySurveyId(long surveyId);

}
