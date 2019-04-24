package com.considLia.survey.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "textquestion")
public class TextQuestion implements Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "question_id")
  private long questionId;
  private String questionTitle;

  public TextQuestion() {}

  public TextQuestion(String questionTitle) {
    this.questionTitle = questionTitle;
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
}
