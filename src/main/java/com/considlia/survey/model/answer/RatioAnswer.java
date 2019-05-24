package com.considlia.survey.model.answer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ratio_answer")
public class RatioAnswer extends Answers {

  private String ratioAnswer;

  public String getRatioAnswer() {
    return ratioAnswer;
  }

  public void setRatioAnswer(String ratioAnswer) {
    this.ratioAnswer = ratioAnswer;
  }
}
