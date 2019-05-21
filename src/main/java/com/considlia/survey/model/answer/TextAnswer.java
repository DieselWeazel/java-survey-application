package com.considlia.survey.model.answer;

import com.considlia.survey.model.answer.Answers;

import javax.persistence.*;

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
}
