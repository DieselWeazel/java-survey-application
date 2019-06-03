package com.considlia.survey.ui.custom_component.layout.answercomponents;

import com.considlia.survey.model.Survey;
import com.considlia.survey.model.SurveyResponse;
import com.considlia.survey.model.question.CheckBoxQuestion;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.question.TextAreaQuestion;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.ui.custom_component.ErrorVerificationMessageDTO;
import com.considlia.survey.ui.custom_component.layout.LoadLayout;
import com.considlia.survey.ui.custom_component.layout.ShowQuestionFactory;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.ShowAnswerLayout;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.SurveyLoader;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowMultiChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowRatioQuestionLayout;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowSingleChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowTextAreaQuestionLayout;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowTextQuestionLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseLoader implements ShowQuestionFactory<List<SurveyResponse>>,
    LoadLayout<ShowAnswerLayout, Question> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResponseLoader.class);

  private Survey survey;


  @Override
  public VerticalLayout getSurveyLayout(Survey survey) {

    return null;
  }

  /**
   * For future, verify if Survey is finished, displays message explaining that Survey will be //
   * removed and finished after completion.
   * @return String explaining Survey can no longer be answered. Can be altered
   * to include a boolean telling if Survey has the amount of responses that the Survey
   * was aiming to get.
   * RETURNS NULL until then
   */
  @Override
  public ErrorVerificationMessageDTO isComplete() {
    return null;
  }

  /**
   * For future, this method could gather the responses and format them into a PDF/Word Document
   * @return list of responses (right now NULL)
   */
  @Override
  public List<SurveyResponse> getList() {
    return null;
  }

  @Override
  public ShowAnswerLayout loadLayout(Question question) {
    LOGGER.info("ReponseLoader: Loading a question");
    if (question instanceof CheckBoxQuestion) {

      LOGGER.info("ReponseLoader: Loading '{}'", question.getTitle());
//      return showMultiChoiceQuestionLayout;
    } else if (question instanceof RadioQuestion) {

      LOGGER.info("ReponseLoader: Loading '{}'", question.getTitle());
//      return showSingleChoiceQuestionLayout;
    } else if (question instanceof TextQuestion) {

      LOGGER.info("ReponseLoader: Loading '{}'", question.getTitle());
//      return showTextQuestionLayout;
    } else if (question instanceof TextAreaQuestion) {

      LOGGER.info("ReponseLoader: loading '{}'", question.getTitle());
//      return showTextAreaQuestionLayout;
    } else if (question instanceof RatioQuestion) {

      LOGGER.info("ReponseLoader: Loading '{}'", question.getTitle());
//      return showRatioQuestionLayout;
    }
    throw new RuntimeException("No Layout Available for Question: " + question.getQuestionType());
  }

}
