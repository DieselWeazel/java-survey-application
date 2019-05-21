package com.considlia.survey.model.answer;

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
@Table(name = "single_choice_answer")
public class SingleChoiceAnswer extends Answers {

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "answer_id")
  @OrderBy("position ASC")
  private Set<AnswerAlternative> answeralternatives = new HashSet<>();

  public Set<AnswerAlternative> getAnsweralternatives() {
    return answeralternatives;
  }

  public void setAnsweralternatives(
      Set<AnswerAlternative> answeralternatives) {
    this.answeralternatives = answeralternatives;
  }
}
