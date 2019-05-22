package com.considlia.survey.ui.custom_component.question_with_button;

import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

@StyleSheet("css/app.css")
public class TextQuestionWithButtons extends QuestionWithButtons {

  private TextField textfield;
  private TextArea textarea;

  public TextQuestionWithButtons(TextQuestion question, CreateSurveyView survey) {

    super(question, survey);

    if (question.getQuestionType() == QuestionType.TEXTFIELD) {
      textfield = new TextField();
      textfield.setWidth("40%");
      textfield.setEnabled(false);
      add(textfield);
    } else if (question.getQuestionType() == QuestionType.TEXTAREA) {
      textarea = new TextArea();
      textarea.setWidth("40%");
      textarea.setHeight("35%");
      textarea.setEnabled(false);
      add(textarea);
    }
  }

}
