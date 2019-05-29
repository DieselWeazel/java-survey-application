package com.considlia.survey.model.answer;

import com.considlia.survey.model.question.MultiQuestionAlternative;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** MultiAnswerChoice, child entity of {@link MultiAnswer}. */
@Entity
@Table(name = "chosen_answer")
public class MultiAnswerChoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chosenanswer_id")
  private Long id;

  private String checkedAnswer;

  /**
   * Empty Constructor
   */
  public MultiAnswerChoice() {
  }

  /**
   * Constructs a MultiAnswerChoice for {@link MultiAnswer}
   *
   * @param checkedAnswer is the chosen answer of {@link MultiQuestionAlternative}.
   */
  public MultiAnswerChoice(String checkedAnswer) {
    this.checkedAnswer = checkedAnswer;
  }

  /**
   * Gets the MultiAnswerChoice of {@link MultiAnswer}.
   *
   * @return the chosen answer of {@link MultiQuestionAlternative}
   */
  public String getCheckedAnswer() {
    return checkedAnswer;
  }

  /**
   * Sets MultiAnswerChoice for {@link MultiAnswer}
   *
   * @param checkedAnswer is the resulted chosen answer to {@link MultiQuestionAlternative}.
   */
  public void setCheckedAnswer(String checkedAnswer) {
    this.checkedAnswer = checkedAnswer;
  }

  /** @return String value of checked answer. */
  @Override
  public String toString() {
    return checkedAnswer;
  }
}
