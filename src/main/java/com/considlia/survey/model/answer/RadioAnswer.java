package com.considlia.survey.model.answer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
