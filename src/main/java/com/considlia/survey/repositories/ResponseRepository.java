package com.considlia.survey.repositories;

import com.considlia.survey.model.SurveyResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<SurveyResponse, Long> {

  List<SurveyResponse> findAllBySurveyId(long SurveyId);
}
