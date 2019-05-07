package com.considlia.survey.repositories;

import com.considlia.survey.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

  Survey getSurveyBySurveyId(long surveyId);

}