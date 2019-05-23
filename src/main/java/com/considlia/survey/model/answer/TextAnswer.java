package com.considlia.survey.model.answer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "textanswer")
public class TextAnswer extends Answers {

  private String textAnswer;

  public String getTextAnswer() {
    return textAnswer;
  }

  public void setTextAnswer(String textAnswer) {
    this.textAnswer = textAnswer;
  }

  @Override
  public String toString() {
    return "TextAnswer: " + textAnswer;
  }
}
