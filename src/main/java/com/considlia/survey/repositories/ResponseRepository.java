package com.considlia.survey.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.considlia.survey.model.SurveyResponses;

public interface ResponseRepository extends JpaRepository<SurveyResponses, Long> {
  List<SurveyResponses> findAllBySurveyId(long SurveyId);
}
