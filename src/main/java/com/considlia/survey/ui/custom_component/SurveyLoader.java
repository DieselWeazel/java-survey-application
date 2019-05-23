package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.question.TextQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SurveyLoader implements ReadQuestionFactory<ReadQuestionComponent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SurveyLoader.class);

  @Override
  public ReadQuestionComponent loadQuestion(Question question) {

    LOGGER.info("SurveyLoader: Loading a question");
    if (question instanceof MultiQuestion) {
      MultiQuestion mq = (MultiQuestion) question;
      ReadMultiQuestionLayout readMultiQuestionLayout = new ReadMultiQuestionLayout(mq);
      LOGGER.info("SurveyLoader: Loading a MultiQuestion");
      return readMultiQuestionLayout;
    } else if (question instanceof TextQuestion) {
      ReadTextQuestionLayout readTextQuestionLayout = new ReadTextQuestionLayout(question);
      LOGGER.info("SurveyLoader: Loading a TextQuestion");
      return readTextQuestionLayout;
    } else if (question instanceof RatioQuestion) {
      RatioQuestion rq = (RatioQuestion) question;
      ReadRatioQuestionLayout ratioQuestionLayout = new ReadRatioQuestionLayout(rq);
      LOGGER.info("SurveyLoader: Loading a RatioQuestion");
      return ratioQuestionLayout;
    }
    return null;
  }
}
