package com.considlia.survey.ui.custom_component.layout.showsurveycomponents;

import com.considlia.survey.model.QuestionType;
import com.considlia.survey.ui.custom_component.layout.ShowQuestionFactory;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowMultiChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowRatioQuestionLayout;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowSingleChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowTextAreaQuestionLayout;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowTextQuestionLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.considlia.survey.model.Survey;
import com.considlia.survey.model.SurveyResponse;
import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.ui.custom_component.ErrorVerificationMessageDTO;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;

/**
 * SurveyLoader implementing ShowQuestionFactory. Displays all Questions and applies functions as if
 * question contains mandatory fields or not.
 *
 * Written by Jonathan Harr
 */
@Service
public class SurveyLoader implements ShowQuestionFactory<Set<Answer>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SurveyLoader.class);

  private Survey survey;

  private List<ShowQuestionLayout> componentList = new ArrayList<>();

  private List<String> errorMessage;

  private boolean hasChanges;

  /**
   * Loads Survey to ShowSurvey.
   * Includes a Change Value Listener for all types of Components, purpose is to handle all sorts of
   * fields that has an input value, and if so, warn the user if they leave the page.
   * The Change Value Listener will react to all objects that implement AbstractField.
   *
   * @param survey input for all Questions.
   * @return layout containing all questions with answering method, as well as all corresponding
   *         functions such as if Question requires an answer or not.
   */
  @Override
  public VerticalLayout getSurveyLayout(Survey survey) {
    this.survey = survey;
    VerticalLayout vr = new VerticalLayout();
    survey.getQuestions().stream().forEach(question -> {
      ShowQuestionLayout layout = loadQuestion(question);
      layout.setMandatoryStatus();
      componentList.add(layout);
      vr.add(layout);
    });

    for(ShowQuestionLayout c : componentList){
      for (int i = 0; i < c.getComponentCount(); i++){
        Component component = c.getComponentAt(i);
        if (component instanceof AbstractField){
          AbstractField abstractField = (AbstractField) component;
          abstractField.addValueChangeListener(e-> hasChanges = true);
        }
      }
    }
    return vr;
  }

  /**
   * If Survey contains mandatory fields, this DTO will check if the Survey is Complete.
   *
   * @return {@link ErrorVerificationMessageDTO} boolean showing true if Survey is in conflict,
   *         therefor not being completed. String message with each Question that isn't filled in.
   */
  @Override
  public ErrorVerificationMessageDTO isComplete() {
    errorMessage = new ArrayList<>();
    errorMessage.add("Error! This Survey contains questions that are mandatory.");
    errorMessage.add("The following questions need to be filled in: ");
    errorMessage.add("");
    boolean isComplete = true;
    for (ShowQuestionLayout s : componentList) {

      if (!s.isCompleted()) {
        errorMessage
            .add((s.getQuestion().getPosition() + 1) + ": " + s.getQuestion().getTitle() + ", ");
        isComplete = false;
      }
    }
    errorMessage.add("");
    errorMessage.add("Answer these Questions before submitting your response.");
    return new ErrorVerificationMessageDTO(isComplete, hasChanges, errorMessage);
  }

  /**
   * Gets the List of Answers, to store within a {@link SurveyResponse}
   *
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
    hasChanges = false;
    return list;
  }

  /**
   * Loads each questions and assigns a layout.
   *
   * @return layout corresponding with Question. {@link ShowQuestionLayout}
   */
  private ShowQuestionLayout loadQuestion(Question question) {

    LOGGER.info("SurveyLoader: Loading a question");
    if (question.getQuestionType().equals(QuestionType.CHECKBOX)) {
      MultiQuestion mq = (MultiQuestion) question;

      LOGGER.info("SurveyLoader: Loading '{}'", mq.getTitle());
      return new ShowMultiChoiceQuestionLayout(mq);
    } else if (question.getQuestionType().equals(QuestionType.RADIO)) {
      RadioQuestion radioQuestion = (RadioQuestion) question;

      LOGGER.info("SurveyLoader: Loading '{}'", radioQuestion.getTitle());
      return new ShowSingleChoiceQuestionLayout(radioQuestion);
    } else if (question.getQuestionType().equals(QuestionType.TEXTFIELD)) {

      LOGGER.info("SurveyLoader: Loading '{}'", question.getTitle());
      return new ShowTextQuestionLayout(question);
    } else if (question.getQuestionType().equals(QuestionType.TEXTAREA)) {

      LOGGER.info("SurveyLoader: loading '{}'", question.getTitle());
      return new ShowTextAreaQuestionLayout(question);
    } else if (question.getQuestionType().equals(QuestionType.RATIO)) {
      RatioQuestion rq = (RatioQuestion) question;

      LOGGER.info("SurveyLoader: Loading '{}'", rq.getTitle());
      return new ShowRatioQuestionLayout(rq);
    }
    throw new RuntimeException("No Layout Available for Question: " + question.getQuestionType());
  }
}
