package com.considlia.survey.model.answer;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class SingleChoiceAnswer extends Answers {

  private ChosenAnswer chosenAnswer;

  public SingleChoiceAnswer(ChosenAnswer chosenAnswer){
    this.chosenAnswer = chosenAnswer;
  }
}
