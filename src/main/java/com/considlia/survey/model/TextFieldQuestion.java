package com.considlia.survey.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "textfieldquestion")
public class TextFieldQuestion extends TextQuestion {
  public TextFieldQuestion() {}

  public TextFieldQuestion(String questionTitle, boolean mandatory) {
    super(questionTitle, QuestionType.TEXTFIELD, mandatory);
  }

}
