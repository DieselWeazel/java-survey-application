package com.considlia.survey.ui.custom_component.question_with_button;

import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.custom_component.QuestionType;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

@StyleSheet("css/app.css")
public class TextQuestionWithButtons extends QuestionWithButtons {

  private TextField textfield;
  private TextArea textarea;
  private QuestionType questionType;

  public TextQuestionWithButtons(String question, CreateSurveyView survey,
      QuestionType questionType, boolean mandatory) {

    super(question, survey, mandatory);

    this.questionType = questionType;

    if (questionType == QuestionType.TEXTFIELD) {
      textfield = new TextField();
      textfield.setWidth("40%");
      textfield.setEnabled(false);
      add(textfield);
    } else if (questionType == QuestionType.TEXTAREA) {
      textarea = new TextArea();
      textarea.setWidth("40%");
      textarea.setHeight("35%");
      textarea.setEnabled(false);
      add(textarea);
    }
  }

  public QuestionType getQuestionType() {
    return questionType;
  }

  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
  }

}
