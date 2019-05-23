package com.considlia.survey.model.answer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "answer_id")
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
    return chosenAnswer.toString();
  }
}
