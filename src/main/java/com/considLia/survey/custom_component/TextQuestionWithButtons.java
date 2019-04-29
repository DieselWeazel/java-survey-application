package com.considLia.survey.custom_component;

import com.considLia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TextQuestionWithButtons extends HorizontalLayout {

  private String question;

  public TextQuestionWithButtons(String question, CreateSurveyView survey) {

    this.question = question;

    setWidth("100%");
    H5 title = new H5(question);
    title.setHeightFull();
    title.setWidth("90%");

    add(title);
    add(new Button(new Icon(VaadinIcon.PENCIL)));
    add(new Button(new Icon(VaadinIcon.TRASH), event -> survey.removeQuestion(event.getSource())));

  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

}
