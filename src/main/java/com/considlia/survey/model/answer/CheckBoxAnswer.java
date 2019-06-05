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
import com.considlia.survey.model.question.MultiQuestionAlternative;

/**
 * CheckBoxAnswer, parent entity of {@link CheckBoxAnswerChoice}. Also child of inheritance from
 * {@link Answer}
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class CheckBoxAnswer extends Answer {

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "answer_id")
  private Set<CheckBoxAnswerChoice> checkBoxAnswerChoiceSet = new HashSet<>();

  /** Empty Constructor */
  public CheckBoxAnswer() {}

  /**
   * Constructor for Answer containing CheckBox Answer.
   *
   * @param multiAnswerChoiceSet is the chosen list of answers to {@link MultiQuestionAlternative}.
   */
  public CheckBoxAnswer(Set<CheckBoxAnswerChoice> multiAnswerChoiceSet) {
    this.checkBoxAnswerChoiceSet = multiAnswerChoiceSet;
  }

  /**
   * Gets list of Chosen Answers
   *
   * @return the list of answers to {@link com.considlia.survey.model.question.MultiQuestion}
   */
  public Set<CheckBoxAnswerChoice> getMultiAnswerChoiceSet() {
    return checkBoxAnswerChoiceSet;
  }

  /**
   * Sets Chosen Answer Set.
   *
   * @param multiAnswerChoiceSet is the resulted chosen answers to
   *        {@link com.considlia.survey.model.question.MultiQuestion}
   */
  public void setMultiAnswerChoiceSet(Set<CheckBoxAnswerChoice> multiAnswerChoiceSet) {
    this.checkBoxAnswerChoiceSet = multiAnswerChoiceSet;
  }

  /** @return String Value of Chosen Answer Set. */
  @Override
  public String toString() {
    return "MultiAnswer{" + "multiAnswerChoiceSet=" + checkBoxAnswerChoiceSet + '}';
  }
}
