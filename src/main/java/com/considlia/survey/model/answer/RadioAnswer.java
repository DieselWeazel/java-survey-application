package com.considlia.survey.model.answer;

import javax.persistence.*;

@Entity
@Table(name = "single_choice_answer")
public class RadioAnswer extends Answers {

  private Integer radioAnswer;

  public Integer getRadioAnswer() {
    return radioAnswer;
  }

  public void setRadioAnswer(Integer radioAnswer) {
    this.radioAnswer = radioAnswer;
  }
}
