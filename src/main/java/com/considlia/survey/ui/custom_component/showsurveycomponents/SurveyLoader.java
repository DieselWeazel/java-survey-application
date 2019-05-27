package com.considlia.survey.ui.custom_component.showsurveycomponents;

import com.considlia.survey.model.question.CheckBoxQuestion;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowMultiChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowRatioQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowSingleChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowTextQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SurveyLoader implements ShowQuestionFactory<ShowQuestionComponent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SurveyLoader.class);

  /**
   * Implementation of Factory, Reads
   *
   * @param question any implementation.
   * @return layout corresponding with Question. {@link
   *     ShowQuestionLayout}
   */
  @Override
  public ShowQuestionComponent loadQuestionLayout(Question question) {

    LOGGER.info("SurveyLoader: Loading a question");
    if (question instanceof CheckBoxQuestion) {
      MultiQuestion mq = (MultiQuestion) question;
      ShowMultiChoiceQuestionLayout showMultiChoiceQuestionLayout =
          new ShowMultiChoiceQuestionLayout(mq);
      LOGGER.info("SurveyLoader: Loading '{}'", mq.getTitle());
      return showMultiChoiceQuestionLayout;
    } else if (question instanceof RadioQuestion) {
      RadioQuestion radioQuestion = (RadioQuestion) question;
      ShowSingleChoiceQuestionLayout readSingleChoiceQuestionLayout =
          new ShowSingleChoiceQuestionLayout(radioQuestion);
      LOGGER.info("SurveyLoader: Loading '{}'", radioQuestion.getTitle());
      return readSingleChoiceQuestionLayout;
    } else if (question instanceof TextQuestion) {
      ShowTextQuestionLayout readTextQuestionLayout = new ShowTextQuestionLayout(question);
      LOGGER.info("SurveyLoader: Loading '{}'", question.getTitle());
      return readTextQuestionLayout;
    } else if (question instanceof RatioQuestion) {
      RatioQuestion rq = (RatioQuestion) question;
      ShowRatioQuestionLayout ratioQuestionLayout = new ShowRatioQuestionLayout(rq);
      LOGGER.info("SurveyLoader: Loading '{}'", rq.getTitle());
      return ratioQuestionLayout;
    }
    return null;
  }
}
