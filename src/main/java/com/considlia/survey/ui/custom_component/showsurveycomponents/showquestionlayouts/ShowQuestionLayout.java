package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.question.Question;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ShowQuestionLayout extends VerticalLayout {

  private H5 title;
  private Question question;
  private static final Logger LOGGER = LoggerFactory.getLogger(ShowQuestionLayout.class);

  private boolean isCompleted;

  // Used for Mandatory message.
  public static final String mandatoryQuestionMessage = "Question is mandatory, please fill in details";

  /**
   * Constructs a generic TextQuestionLayout
   *
   * @param question works with any abstract Question.
   */
  public ShowQuestionLayout(Question question) {
    this.title = new H5(question.getTitle() + (question.isMandatory() ? "*" : ""));
    this.question = question;
    LOGGER.info("Loading Question: '{}'", question.getTitle());
    LOGGER.info("Question is mandatory: '{}'", question.isMandatory());
    add(title);
  }

  /** @return any type of Question that implements Question. */
  public Question getQuestion() {
    return question;
  }

  /** @return Logger for debugging purposes. */
  public static Logger getLOGGER() {
    return LOGGER;
  }

  public abstract Answer gatherResponse() throws ValidationException;

  public boolean isCompleted() {
    if(question.isMandatory()){
      return isCompleted(question);
    } else {
      return true;
    }
  }

  public abstract void setMandatoryStatus();

  public abstract boolean isCompleted(Question question);

  public void setCompleted(boolean completed) {
    isCompleted = completed;
  }
}
