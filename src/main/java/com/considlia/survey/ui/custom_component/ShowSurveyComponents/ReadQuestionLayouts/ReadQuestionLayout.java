package com.considlia.survey.ui.custom_component.ShowSurveyComponents.ReadQuestionLayouts;

import com.considlia.survey.model.question.Question;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadQuestionLayout extends VerticalLayout {

  private H5 title;
  private Question question;
  private static final Logger LOGGER = LoggerFactory.getLogger(ReadQuestionLayout.class);

  /**
   * Constructs a generic TextQuestionLayout
   * @param question works with any abstract Question.
   */
  public ReadQuestionLayout(Question question) {
    this.title = new H5(question.getTitle() + (question.isMandatory() ? "*" : ""));
    this.question = question;
    add(title);
  }

  /**
   * @return any type of Question that implements Question.
   */
  public Question getQuestion() {
    return question;
  }

  /**
   * @return Logger for debugging purposes.
   */
  public static Logger getLOGGER() {
    return LOGGER;
  }
}
