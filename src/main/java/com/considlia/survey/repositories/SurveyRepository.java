package com.considlia.survey.repositories;

import com.considlia.survey.model.Survey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

  Survey getSurveyById(long id);

  List<Survey> findAllByUserId(Long id);
}
