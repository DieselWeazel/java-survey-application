package com.considLia.survey.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "multiquestion")
public class MultiQuestion implements Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "question_id")
  private long questionId;
  private String questionTitle;
  private int questionType;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "multiq_multia", joinColumns = @JoinColumn(name = "question_id"),
      inverseJoinColumns = @JoinColumn(name = "alternative_id"))
  private Set<MultiQuestionAlternative> alternativeList;

  public MultiQuestion() {}

  public MultiQuestion(String questionTitle, int questionType) {
    this.questionTitle = questionTitle;
    this.questionType = questionType;
    alternativeList = new HashSet<>();

  }

  public String getQuestionTitle() {
    return questionTitle;
  }

  public void setQuestionTitle(String questionTitle) {
    this.questionTitle = questionTitle;
  }

  public long getQuestionId() {
    return questionId;
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
