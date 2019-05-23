package com.considlia.survey.model.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "multiqalt")
public class MultiQuestionAlternative {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "alternative_id")
  private Long id;

  private String title;
  private int position;

  public MultiQuestionAlternative() {
  }

  public MultiQuestionAlternative(String questionTitle) {
    this.title = questionTitle;
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

  @Override
  public String toString() {
    return title;
  }
}