package com.considlia.survey.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "textareaquestion")
public class TextAreaQuestion extends TextQuestion {

  public TextAreaQuestion() {}

  public TextAreaQuestion(String questionTitle, boolean mandatory) {
    super(questionTitle, QuestionType.TEXTAREA, mandatory);
  }

}
