package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Question;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class ReadQuestionLayout extends VerticalLayout {

  private H5 title;
  private Question question;

  public ReadQuestionLayout(Question question) {
    this.title = new H5(question.getTitle() + (question.isMandatory() ? "*" : ""));
    this.question = question;

    add(title);
  }

  public Question getQuestion() {
    return question;
  }

}
