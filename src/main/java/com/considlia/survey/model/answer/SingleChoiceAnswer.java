package com.considlia.survey.model.answer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class SingleChoiceAnswer extends Answers {

  @OneToOne
  @JoinColumn(name = "chosenanswer_id")
  private ChosenAnswer chosenAnswer;

  public SingleChoiceAnswer(ChosenAnswer chosenAnswer){
    this.chosenAnswer = chosenAnswer;
  }
}
