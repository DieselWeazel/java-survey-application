package com.considlia.survey.repositories;

import com.considlia.survey.model.Survey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

  /*
  TODO change me to Long and try me
   */
  Survey getSurveyById(long id);

  //  @Query(
  //      value =
  //          "select s.survey_id, s.creator, s.date, s.description, s.title from user u "
  //              + "inner join survey s on u.user_id=s.user_id WHERE u.user_id = ?1",
  //      nativeQuery = true)
  List<Survey> findAllByUserId(Long id);
}
