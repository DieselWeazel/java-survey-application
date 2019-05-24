package com.considlia.survey.model.answer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "textanswer")
public class TextAnswer extends Answer {

  private String textAnswer;

  /**
   * Gets the TextAnswer
   * @return the TextAnswer to
   * {@link com.considlia.survey.model.question.TextQuestion} &&
   * {@link com.considlia.survey.model.question.TextAreaQuestion}
   */
  public String getTextAnswer() {
    return textAnswer;
  }

  /**
   * Sets TextAnswer
   * @param textAnswer to
   * {@link com.considlia.survey.model.question.TextQuestion} &&
   * {@link com.considlia.survey.model.question.TextAreaQuestion}
   */
  public void setTextAnswer(String textAnswer) {
    this.textAnswer = textAnswer;
  }

  @Override
  public String toString() {
    return "TextAnswer: " + textAnswer;
  }
}
