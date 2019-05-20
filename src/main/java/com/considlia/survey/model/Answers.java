package com.considlia.survey.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "answers")
public class Answers {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "answer_id")
  private Long id;

  //TODO Delete?

  @OneToOne
  private Question question;
  // private int answerType;

  private String answer;

  public Answers() {}

  public Answers(String answer, Question question) {
    this.answer = answer;
    this.question = question;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public Long getId() {
    return id;
  }

  public Question getQuestion() {
    return question;
  }

  @Override
  public String toString() {
    return answer;
  }

}
