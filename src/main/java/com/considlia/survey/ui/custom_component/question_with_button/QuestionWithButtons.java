package com.considlia.survey.ui.custom_component.question_with_button;

import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.custom_component.ConfirmDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class QuestionWithButtons extends VerticalLayout {

  private static final int MOVE_UP = -1;
  private static final int MOVE_DOWN = 1;

  private String question;
  private boolean mandatory;
  private H5 title;

  private Button upButton;
  private Button downButton;

  private HorizontalLayout content;
  private CreateSurveyView survey;

  public QuestionWithButtons(String question, CreateSurveyView survey, boolean mandatory) {
    setId("custom");
    setWidth("100%");

    this.question = question;
    this.survey = survey;
    this.upButton = new Button(new Icon(VaadinIcon.ARROW_UP));
    this.downButton = new Button(new Icon(VaadinIcon.ARROW_DOWN));
    setMandatory(mandatory);

    content = new HorizontalLayout();
    content.setWidthFull();
    content.setClassName("content");

    title = new H5(question + (mandatory ? "*" : ""));
    title.setWidth("90%");

    initButtonEvent(upButton, MOVE_UP);
    initButtonEvent(downButton, MOVE_DOWN);

    content.add(title, upButton, downButton);
    content.add(
        new Button(new Icon(VaadinIcon.PENCIL), event -> survey.editQuestion(event.getSource())));
    content.add(new Button(new Icon(VaadinIcon.TRASH), event -> removeQuestion(survey)));

    add(content);

  }

  public void initButtonEvent(Button button, int move) {
    button.addClickListener(onClick -> {
      survey.moveQuestion(onClick.getSource(), move);
      survey.updateMoveButtonStatus();
    });
  }

  public void removeQuestion(CreateSurveyView survey) {
    new ConfirmDialog(survey, this);
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;

    H5 updatedTitle = new H5(question);
    updatedTitle.setWidth("90%");

    content.replace(title, updatedTitle);
    title = updatedTitle;
  }

  public H5 getTitle() {
    return title;
  }

  public void setTitle(H5 title) {
    this.title = title;
  }

  public boolean isMandatory() {
    return mandatory;
  }

  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
  }

  public Button getUpButton() {
    return upButton;
  }

  public void setUpButton(Button upButton) {
    this.upButton = upButton;
  }

  public Button getDownButton() {
    return downButton;
  }

  public void setDownButton(Button downButton) {
    this.downButton = downButton;
  }

  public HorizontalLayout getContent() {
    return content;
  }

  public void setContent(HorizontalLayout content) {
    this.content = content;
  }

}
