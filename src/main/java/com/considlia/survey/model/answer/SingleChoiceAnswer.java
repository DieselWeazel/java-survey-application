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

  public SingleChoiceAnswer (){}

  public SingleChoiceAnswer(ChosenAnswer chosenAnswer){
    this.chosenAnswer = chosenAnswer;
  }

  public ChosenAnswer getChosenAnswer() {
    return chosenAnswer;
  }

  public void setChosenAnswer(ChosenAnswer chosenAnswer) {
    this.chosenAnswer = chosenAnswer;
  }

  @Override
  public String toString() {
    return "SingleChoiceAnswer{" +
        "chosenAnswer=" + chosenAnswer +
        '}';
  }
}
