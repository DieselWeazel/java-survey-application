package com.considlia.survey.model.answer;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "ratio_answer")
public class RatioAnswer extends Answers {

  private Integer ratioAnswer;

  public Integer getRatioAnswer() {
    return ratioAnswer;
  }

  public void setRatioAnswer(Integer ratioAnswer) {
    this.ratioAnswer = ratioAnswer;
  }
}
