package com.considlia.survey.ui.custom_component.showsurveycomponents;

import com.considlia.survey.model.Survey;
import com.considlia.survey.model.SurveyResponse;
import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.question.CheckBoxQuestion;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.question.TextAreaQuestion;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.ui.custom_component.ErrorVerificationMessageDTO;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowMultiChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowRatioQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowSingleChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowTextAreaQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowTextQuestionLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * SurveyLoader implementing ShowQuestionFactory.
 * Displays all Questions and applies functions as if question
 * contains mandatory fields or not.
 *
 * Written by Jonathan Harr
 */
@Service
public class SurveyLoader
    implements ShowQuestionFactory<Set<Answer>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SurveyLoader.class);

  private Survey survey;

  private List<ShowQuestionLayout> componentList = new ArrayList<>();

  private List<String> errorMessage;

  /**
   * Loads Survey to ShowSurvey.
   * @param survey input for all Questions.
   * @return layout containing all questions with answering method, as well as all
   * corresponding functions such as if Question requires an answer or not.
   */
  @Override
  public VerticalLayout getSurveyLayout(Survey survey){
    this.survey=survey;
    VerticalLayout vr = new VerticalLayout();
    survey.getQuestions().stream().forEach(question ->{
      ShowQuestionLayout layout = loadQuestion(question);
      layout.setMandatoryStatus();
      componentList.add(layout);
      vr.add(layout);
    });

    return vr;
  }

  /**
   * If Survey contains mandatory fields, this DTO will check if the Survey is Complete.
   * @return {@link ErrorVerificationMessageDTO}
   * boolean showing true if Survey is in conflict, therefor not being completed.
   * String message with each Question that isn't filled in.
   */
  @Override
  public ErrorVerificationMessageDTO isComplete() {
    errorMessage = new ArrayList<>();
    errorMessage.add("Error! This Survey contains questions that are mandatory.");
    errorMessage.add("The following questions need to be filled in: ");
    errorMessage.add("");
    boolean isComplete = true;
    for (ShowQuestionLayout s : componentList){

      if (!s.isCompleted()){
        errorMessage.add((s.getQuestion().getPosition()+1) + ": " + s.getQuestion().getTitle() + ", ");
        isComplete = false;
      }
    }
    errorMessage.add("");
    errorMessage.add("Answer these Questions before submitting your response.");
    return new ErrorVerificationMessageDTO(isComplete, errorMessage);
  }

  /**
   * Gets the List of Answers, to store within a {@link SurveyResponse}
   * @return Answers to {@link Question} within {@link Survey}
   */
  @Override
  public Set<Answer> getList() {
    Set<Answer> list = new HashSet<>();
    componentList.forEach(question -> {
      try {
        list.add(question.gatherResponse());
      } catch (ValidationException e) {
        e.printStackTrace();
      }
    });
    return list;
  }


  /**
   * Loads each questions and assigns a layout.
   *
   * @return layout corresponding with Question. {@link ShowQuestionLayout}
   */
  private ShowQuestionLayout loadQuestion(Question question) {

    LOGGER.info("SurveyLoader: Loading a question");
    if (question instanceof CheckBoxQuestion) {
      MultiQuestion mq = (MultiQuestion) question;
      ShowMultiChoiceQuestionLayout showMultiChoiceQuestionLayout =
          new ShowMultiChoiceQuestionLayout(mq);
      LOGGER.info("SurveyLoader: Loading '{}'", mq.getTitle());
      return showMultiChoiceQuestionLayout;
    } else if (question instanceof RadioQuestion) {
      RadioQuestion radioQuestion = (RadioQuestion) question;
      ShowSingleChoiceQuestionLayout showSingleChoiceQuestionLayout =
          new ShowSingleChoiceQuestionLayout(radioQuestion);
      LOGGER.info("SurveyLoader: Loading '{}'", radioQuestion.getTitle());
      return showSingleChoiceQuestionLayout;
    } else if (question instanceof TextQuestion) {
      ShowTextQuestionLayout showTextQuestionLayout = new ShowTextQuestionLayout(question);
      LOGGER.info("SurveyLoader: Loading '{}'", question.getTitle());
      return showTextQuestionLayout;
    } else if (question instanceof TextAreaQuestion) {
      ShowTextAreaQuestionLayout showTextAreaQuestionLayout = new ShowTextAreaQuestionLayout(question);
      LOGGER.info("SurveyLoader: loading '{}'", question.getTitle());
      return showTextAreaQuestionLayout;
    } else if (question instanceof RatioQuestion) {
      RatioQuestion rq = (RatioQuestion) question;
      ShowRatioQuestionLayout showRatioQuestionLayout = new ShowRatioQuestionLayout(rq);
      LOGGER.info("SurveyLoader: Loading '{}'", rq.getTitle());
      return showRatioQuestionLayout;
    }
    throw new RuntimeException("No Layout Available for Question: " + question.getQuestionType());
  }
}
