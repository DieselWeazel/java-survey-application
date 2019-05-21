package com.considlia.survey.model.question;

import javax.persistence.*;

import com.considlia.survey.model.Survey;
import com.considlia.survey.ui.custom_component.QuestionType;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "question_id")
  private Long id;

  private String title;
  private int position;
  private QuestionType questionType;
  private boolean mandatory;

  public Question() {}

  public Question(String questionTitle, int position, QuestionType questionType,
      boolean mandatory) {
    this.title = questionTitle;
    this.position = position;
    this.mandatory = mandatory;
    this.questionType = questionType;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public QuestionType getQuestionType() {
    return questionType;
  }

  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
  }

  public boolean isMandatory() {
    return mandatory;
  }

  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String toString() {
    return getTitle();
  }
}
