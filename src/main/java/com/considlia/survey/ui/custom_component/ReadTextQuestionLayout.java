package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Question;
import com.vaadin.flow.component.textfield.TextField;

public class ReadTextQuestionLayout extends ReadQuestionLayout {

  private TextField questionField;

  public ReadTextQuestionLayout(Question question) {
    super(question);
    questionField = new TextField();

    add(questionField);
  }

  public TextField getQuestionField() {
    return questionField;
  }
}
