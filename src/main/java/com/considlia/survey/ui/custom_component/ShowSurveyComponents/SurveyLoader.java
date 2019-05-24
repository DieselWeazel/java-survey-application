package com.considlia.survey.ui.custom_component.ShowSurveyComponents;

import com.considlia.survey.model.question.CheckBoxQuestion;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.ui.custom_component.ShowSurveyComponents.ReadQuestionLayouts.ReadMultiChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.ShowSurveyComponents.ReadQuestionLayouts.ReadRatioQuestionLayout;
import com.considlia.survey.ui.custom_component.ShowSurveyComponents.ReadQuestionLayouts.ReadSingleChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.ShowSurveyComponents.ReadQuestionLayouts.ReadTextQuestionLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SurveyLoader implements ReadQuestionFactory<ReadQuestionComponent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SurveyLoader.class);

  /**
   * Implementation of Factory, Reads
   * @param question any implementation.
   * @return layout corresponding with Question.
   */
  @Override
  public ReadQuestionComponent loadQuestionLayout(Question question) {

    LOGGER.info("SurveyLoader: Loading a question");
    if (question instanceof CheckBoxQuestion) {
      MultiQuestion mq = (MultiQuestion) question;
      ReadMultiChoiceQuestionLayout readMultiChoiceQuestionLayout = new ReadMultiChoiceQuestionLayout(mq);
      LOGGER.info("SurveyLoader: Loading '{}'", mq.getTitle());
      return readMultiChoiceQuestionLayout;
    } else if (question instanceof RadioQuestion){
      RadioQuestion radioQuestion = (RadioQuestion) question;
      ReadSingleChoiceQuestionLayout readSingleChoiceQuestionLayout = new ReadSingleChoiceQuestionLayout(radioQuestion);
      LOGGER.info("SurveyLoader: Loading '{}'", radioQuestion.getTitle());
      return readSingleChoiceQuestionLayout;
    }else if (question instanceof TextQuestion) {
      ReadTextQuestionLayout readTextQuestionLayout = new ReadTextQuestionLayout(question);
      LOGGER.info("SurveyLoader: Loading '{}'", question.getTitle());
      return readTextQuestionLayout;
    } else if (question instanceof RatioQuestion) {
      RatioQuestion rq = (RatioQuestion) question;
      ReadRatioQuestionLayout ratioQuestionLayout = new ReadRatioQuestionLayout(rq);
      LOGGER.info("SurveyLoader: Loading '{}'", rq.getTitle());
      return ratioQuestionLayout;
    }
    return null;
  }
}
