package com.considlia.survey.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "multiquestion")
public class MultiQuestion extends Question {

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "question_id")
  @OrderBy("position ASC")
  private Set<MultiQuestionAlternative> alternatives = new HashSet<>();

  public Set<MultiQuestionAlternative> getAlternatives() {
    return alternatives;
  }

  public void setAlternatives(Set<MultiQuestionAlternative> alternatives) {
    this.alternatives = alternatives;
  }

  public void addAlternative(MultiQuestionAlternative mqa) {
    getAlternatives().add(mqa);
  }

  public void moveAlternative(MultiQuestionAlternative mqa, int moveDirection) {
    mqa.setPosition(mqa.getPosition() + moveDirection);
  }

  @Override
  public String toString() {
    return "MultiQuestion [alternativeList=" + alternatives + ", questionType=" + "]";
  }

}
