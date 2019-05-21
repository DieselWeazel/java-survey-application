package com.considlia.survey.model.answer;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "checkbox_answer")
public class CheckBoxAnswer extends Answers {

  @ElementCollection
  @CollectionTable(name = "checkbox_answer_set")
  private Set<Integer> checkBoxAnswers = new HashSet<>();

  public Set<Integer> getCheckBoxAnswers() {
    return checkBoxAnswers;
  }

  public void setCheckBoxAnswers(Set<Integer> checkBoxAnswers) {
    this.checkBoxAnswers = checkBoxAnswers;
  }
}
