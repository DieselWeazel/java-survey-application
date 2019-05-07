package com.considlia.survey.custom_component;

import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

@StyleSheet("css/app.css")
public class TextQuestionWithButtons extends VerticalLayout {

  private static final int MOVE_UP = -1;
  private static final int MOVE_DOWN = 1;

  private String question;
  private H5 title;
  private TextField text;

  private Button upButton;
  private Button downButton;

  private HorizontalLayout content;

  public TextQuestionWithButtons(String question, CreateSurveyView survey) {
    setId("custom");
    setWidth("100%");

    this.question = question;
    this.upButton = new Button(new Icon(VaadinIcon.ARROW_UP));
    this.downButton = new Button(new Icon(VaadinIcon.ARROW_DOWN));

    content = new HorizontalLayout();
    content.setClassName("content");
    content.setWidthFull();
    title = new H5(question);
    title.setHeightFull();
    title.setWidth("90%");
    text = new TextField();
    text.setWidth("25%");
    text.setEnabled(false);

    content.add(title);

    upButton.addClickListener(e -> {
      survey.moveQuestion(e.getSource(), MOVE_UP);
      survey.refreshQuestions();
    });
    downButton.addClickListener(e -> {
      survey.moveQuestion(e.getSource(), MOVE_DOWN);
      survey.refreshQuestions();
    });

    content.add(upButton);
    content.add(downButton);
    content.add(
        new Button(new Icon(VaadinIcon.PENCIL), event -> survey.editQuestion(event.getSource())));
    content.add(
        new Button(new Icon(VaadinIcon.TRASH), event -> removeQuestion(survey)));

    add(content);
    add(text);

  }

  public Dialog removeQuestion(CreateSurveyView survey) {
    Dialog dialog = new Dialog();
    dialog.setCloseOnOutsideClick(false);
    NativeButton confirmButton = new NativeButton("Are you sure you want to remove this question?",
        e -> {
          survey.removeQuestion(this);
          dialog.close();
        });
    NativeButton cancelButton = new NativeButton("Cancel", e -> {
      dialog.close();
    });

    dialog.add(confirmButton, cancelButton);
    dialog.open();
    return dialog;
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

  public Button getUpButton() {
    return upButton;
  }

  public Button getDownButton() {
    return downButton;
  }
}

