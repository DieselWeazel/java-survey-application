package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.question.Question;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base layout to show Questions within {@link com.considlia.survey.ui.ShowSurveyView}
 *
 * Written by Jonathan Harr
 */
public abstract class ShowQuestionLayout extends VerticalLayout {

  protected Question question;
  private static final Logger LOGGER = LoggerFactory.getLogger(ShowQuestionLayout.class);

  // Used for Mandatory message.
  public static final String MANDATORY_QUESTION_MESSAGE = "Question is mandatory, please fill in details";

  /**
   * Constructs a generic TextQuestionLayout
   *
   * @param question works with any abstract Question.
   */
  public ShowQuestionLayout(Question question) {
    add(new H5(question.getTitle() + (question.isMandatory() ? "*" : "")));
    this.question = question;

    LOGGER.info("Loading Question: '{}'", question.getTitle());
    LOGGER.info("Question is mandatory: '{}'", question.isMandatory());
  }

  /** @return any type of Question that implements Question. */
  public Question getQuestion() {
    return question;
  }

  /** @return Logger for debugging purposes. */
  public static Logger getLOGGER() {
    return LOGGER;
  }

  /**
   * Abstract Method, each type of ShowQuestionLayout needs to gather its answer accoridingly.
   * @return Answer
   * @throws ValidationException
   */
  public abstract Answer gatherResponse() throws ValidationException;

  /**
   * Checks if Answer is finished.
   * @return true if Question is mandatory, and Answer is filled in.
   * Also true if Question is not mandatory, and Answer is not filled in.
   * Is false if Question is mandatory and Answer is not filled in.
   */
  public boolean isCompleted() {
    if(question.isMandatory()){
      return isCompleted(question);
    } else {
      return true;
    }
  }

  /**
   * Abstract method, each type of ShowQuestionLayout needs to check if it's
   * corresponding {@link Question} is mandatory.
   * @param question
   * @return true if Question is mandatory, and Answer filled in.
   * false if Question is mandatory, and Answer is not filled in.
   */
  public abstract boolean isCompleted(Question question);

  /**
   * Abstract method, each type of ShowQuestionLayout needs to verify the
   * mandatory status of {@link Question}
   */
  public abstract void setMandatoryStatus();
}
