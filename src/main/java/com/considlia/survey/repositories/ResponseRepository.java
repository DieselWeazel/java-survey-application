package com.considlia.survey.repositories;

import com.considlia.survey.model.SurveyResponses;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<SurveyResponses, Long> {

  List<SurveyResponses> findAllBySurveyId(long SurveyId);
}
