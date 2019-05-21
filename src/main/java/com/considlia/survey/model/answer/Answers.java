package com.considlia.survey.model.answer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Answers {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "answer_id")
  private Long id;

  private int questionPosition;
  public Answers() {}

  public Long getId() {
    return id;
  }

  public int getPosition() {
    return questionPosition;
  }

  public void setPosition(int questionPosition) {
    this.questionPosition = questionPosition;
  }

}