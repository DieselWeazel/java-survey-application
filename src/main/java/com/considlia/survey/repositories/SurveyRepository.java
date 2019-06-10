package com.considlia.survey.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.considlia.survey.model.Survey;
import com.considlia.survey.model.User;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

  Survey getSurveyById(long id);

  List<Survey> findAllByUserId(Long id);

  /**
   * Returns all instances of the type with the given user and title
   * 
   * @param title - {@link String} title of the Survey
   * @param user - {@link User} that created the survey
   * @return {@link List} of {@link Survey}s
   */
  @Query("FROM Survey where LOWER(title) = LOWER(:title) AND user_id = :user")
  List<Survey> findByUserAndTitle(@Param("title") String title, @Param("user") User user);
}
