package com.considlia.survey.model.answer;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class MultiChoiceAnswer extends Answers {

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "answer_id")
  private Set<ChosenAnswer> chosenAnswerSet = new HashSet<>();

  public MultiChoiceAnswer(){}

  public MultiChoiceAnswer(
      Set<ChosenAnswer> chosenAnswerSet) {
    this.chosenAnswerSet = chosenAnswerSet;
  }

  public Set<ChosenAnswer> getChosenAnswerSet() {
    return chosenAnswerSet;
  }

  public void setChosenAnswerSet(
      Set<ChosenAnswer> chosenAnswerSet) {
    this.chosenAnswerSet = chosenAnswerSet;
  }
}
