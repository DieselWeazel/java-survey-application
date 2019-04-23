package com.considLia.survey.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long questionId;

  private String questionTitle;

  public Question() {}


  public Question(String questionTitle) {
    setQuestionTitle(questionTitle);
  }

  public long getQuestionId() {
    return questionId;
  }



  public String getQuestionTitle() {
    return questionTitle;
  }

  public void setQuestionTitle(String questionTitle) {
    this.questionTitle = questionTitle;
  }


  @Override
  public String toString() {
    return "Question [questionId=" + questionId + ", questionTitle=" + questionTitle + "]";
  }



}
