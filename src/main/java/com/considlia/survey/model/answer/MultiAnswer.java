package com.considlia.survey.model.answer;

import com.considlia.survey.model.question.MultiQuestionAlternative;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
/** MultiAnswer, parent entity of {@link MultiAnswerChoice}. */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class MultiAnswer extends Answer {

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "answer_id")
  private Set<MultiAnswerChoice> multiAnswerChoiceSet = new HashSet<>();

  /** Empty Constructor */
  public MultiAnswer() {}

  /**
   * Constructor for Answer containing MultiQuestion Answer.
   *
   * @param multiAnswerChoiceSet is the chosen list of answers to {@link MultiQuestionAlternative}.
   */
  public MultiAnswer(Set<MultiAnswerChoice> multiAnswerChoiceSet) {
    this.multiAnswerChoiceSet = multiAnswerChoiceSet;
  }

  /**
   * Gets list of Chosen Answers
   *
   * @return the list of answers to {@link com.considlia.survey.model.question.MultiQuestion}
   */
  public Set<MultiAnswerChoice> getMultiAnswerChoiceSet() {
    return multiAnswerChoiceSet;
  }

  /**
   * Sets Chosen Answer Set.
   *
   * @param multiAnswerChoiceSet is the resulted chosen answers to {@link
   *     com.considlia.survey.model.question.MultiQuestion}
   */
  public void setMultiAnswerChoiceSet(Set<MultiAnswerChoice> multiAnswerChoiceSet) {
    this.multiAnswerChoiceSet = multiAnswerChoiceSet;
  }
  /** @return String Value of Chosen Answer Set. */
  @Override
  public String toString() {
    return "MultiAnswer{" + "multiAnswerChoiceSet=" + multiAnswerChoiceSet + '}';
  }
}
