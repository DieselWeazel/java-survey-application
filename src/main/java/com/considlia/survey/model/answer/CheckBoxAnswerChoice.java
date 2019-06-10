package com.considlia.survey.model.answer;

import com.considlia.survey.model.question.MultiQuestionAlternative;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** MultiAnswerChoice, child entity of {@link CheckBoxAnswer}. */
@Entity
@Table(name = "chosen_answer")
public class CheckBoxAnswerChoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chosenanswer_id")
  private Long id;

  private String checkedAnswer;

  /**
   * Empty Constructor
   */
  public CheckBoxAnswerChoice() {
  }

  /**
   * Constructs a MultiAnswerChoice for {@link CheckBoxAnswer}
   *
   * @param checkedAnswer is the chosen answer of {@link MultiQuestionAlternative}.
   */
  public CheckBoxAnswerChoice(String checkedAnswer) {
    this.checkedAnswer = checkedAnswer;
  }

  /**
   * Gets the MultiAnswerChoice of {@link CheckBoxAnswer}.
   *
   * @return the chosen answer of {@link MultiQuestionAlternative}
   */
  public String getCheckedAnswer() {
    return checkedAnswer;
  }

  /**
   * Sets MultiAnswerChoice for {@link CheckBoxAnswer}
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
