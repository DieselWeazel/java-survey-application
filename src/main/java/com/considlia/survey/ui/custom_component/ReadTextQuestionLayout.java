package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Question;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ReadTextQuestionLayout extends VerticalLayout {

  private HorizontalLayout horizontalLayout;
  private Question question;

  private H5 titleOfQuestion;
  private TextField questionField;

  public ReadTextQuestionLayout(Question question) {
    this.horizontalLayout = new HorizontalLayout();
    this.question = question;
    questionField = new TextField();
    titleOfQuestion = new H5(question.getTitle());

    horizontalLayout.add(questionField);
    add(titleOfQuestion, horizontalLayout);

  }
}