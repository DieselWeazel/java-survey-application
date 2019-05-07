package com.considlia.survey.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "question_id")
  private Long questionId;
  private String questionTitle;
  private int position;

  public Question() {
  }

  public Question(String questionTitle, int position) {
    this.questionTitle = questionTitle;
    this.position = position;
  }

  public String getQuestionTitle() {
    return questionTitle;
  }

  public void setQuestionTitle(String questionTitle) {
    this.questionTitle = questionTitle;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public Long getQuestionId() {
    return questionId;
  }

  @Override
  public String toString() {
    return getQuestionTitle();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Question question = (Question) o;
    return questionId.equals(question.questionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(questionId);
  }
}
