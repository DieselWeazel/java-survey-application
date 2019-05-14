package com.considlia.survey.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ratingquestion")
public class RatioQuestion extends Question {

  private String start;
  private String end;
  private int choices;

  public RatioQuestion() {}

  public RatioQuestion(String questionTitle, int position, boolean mandatory, String start,
      String end, int choices) {
    super(questionTitle, position, mandatory);

    this.start = start;
    this.end = end;
    this.choices = choices;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public int getChoices() {
    return choices;
  }

  public void setChoices(int choices) {
    this.choices = choices;
  }
}
