package com.considlia.survey.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.considlia.survey.model.SurveyResponse;

public interface ResponseRepository extends JpaRepository<SurveyResponse, Long> {

  List<SurveyResponse> findAllBySurveyId(long SurveyId);

  List<SurveyResponse> findAllByUserId(Long id);
}
