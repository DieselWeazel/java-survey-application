package com.considlia.survey.model.answer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Answers {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
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