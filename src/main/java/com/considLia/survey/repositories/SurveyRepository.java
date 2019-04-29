package com.considLia.survey.repositories;


import java.util.List;

import com.considLia.survey.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import com.considLia.survey.model.Survey;
import com.considLia.survey.model.TextQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
  Survey getSurveyBySurveyId(long surveyId);

//  List<Question> getQuestionByPosition()

//@Query("select a from Article a where a.creationDateTime <= :creationDateTime")
  ////List<Article> findAllWithCreationDateTimeBefore(
  ////        @Param("creationDateTime") Date creationDateTime);
  //
  //  @Query()
  //  List<Question> findByQuestionOrderPosition(Survey survey, int position);

//  @Query("select * from question q inner join survey s on q.survey_id=s.survey_id where s.survey_id = :survey_id")
//  List<Survey> findAllWithSurveyId(@Param(""))

  List<TextQuestion> getQuestionBySurveyId(long surveyId);
}
