package com.considLia.survey.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "multiquestion")
public class MultiQuestion extends Question {

  private int questionType;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "question_id")
  private Set<MultiQuestionAlternative> alternativeList;

  public MultiQuestion() {
    super();
  }

  public MultiQuestion(String questionTitle, int position, int questionType) {
    super(questionTitle, position);
    this.questionType = questionType;
    alternativeList = new HashSet<>();

  }

  public Set<MultiQuestionAlternative> getAlternativeList() {
    return alternativeList;
  }

  public void setAlternativeList(Set<MultiQuestionAlternative> alternativeList) {
    this.alternativeList = alternativeList;
  }

  public int getQuestionType() {
    return questionType;
  }

  public void setQuestionType(int questionType) {
    this.questionType = questionType;
  }

  @Override
  public String toString() {
    return "MultiQuestion [alternativeList=" + alternativeList + ", questionType=" + questionType
        + "]";
  }

}
