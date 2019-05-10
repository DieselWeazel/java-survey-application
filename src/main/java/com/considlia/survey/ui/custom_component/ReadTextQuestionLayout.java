package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Question;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ReadTextQuestionLayout extends VerticalLayout {

  private H5 titleOfQuestion;
  private TextField questionField;

  public ReadTextQuestionLayout(Question question) {
    questionField = new TextField();
    titleOfQuestion = new H5(question.getTitle() + (question.isMandatory() ? "*" : ""));

    add(titleOfQuestion, questionField);

  }
}
