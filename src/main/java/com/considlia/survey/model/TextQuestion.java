package com.considlia.survey.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "textquestion")
public class TextQuestion extends Question {

  public TextQuestion() {
    super();
  }

}
