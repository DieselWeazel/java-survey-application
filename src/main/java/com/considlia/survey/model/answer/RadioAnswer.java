package com.considlia.survey.model.answer;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Child of inheritance from {@link Answer}
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class RadioAnswer extends Answer {

  private String chosenAnswer;

  /** Empty Constructor */
  public RadioAnswer() {}

  /**
   * Constructor for Answer to RadioQuestion
   *
   * @param chosenAnswer is the chosen answer to {@link
   *     com.considlia.survey.model.question.RadioQuestion}
   */
  public RadioAnswer(String chosenAnswer) {
    this.chosenAnswer = chosenAnswer;
  }

  /**
   * gets the chosen answer
   *
   * @return the chosen answer of {@link com.considlia.survey.model.question.RadioQuestion}
   */
  public String getChosenAnswer() {
    return chosenAnswer;
  }

  /**
   * Sets the Chosen Answer.
   *
   * @param chosenAnswer is the resulet
   */
  public void setChosenAnswer(String chosenAnswer) {
    this.chosenAnswer = chosenAnswer;
  }

  @Override
  public String toString() {
    return chosenAnswer;
  }
}
