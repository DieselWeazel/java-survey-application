package com.considlia.survey.ui.custom_component.question_with_button;

import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.textfield.TextField;

@StyleSheet("css/app.css")
public class TextQuestionWithButtons extends QuestionWithButtons {

  private TextField text;

  public TextQuestionWithButtons(String question, CreateSurveyView survey, boolean mandatory) {
    super(question, survey, mandatory);

    text = new TextField();
    text.setWidth("25%");
    text.setEnabled(false);

    add(text);
  }
}
