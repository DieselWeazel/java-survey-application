package com.considlia.survey.model.question;

import com.considlia.survey.model.QuestionType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ratingquestion")
public class RatioQuestion extends Question {

  private String start;
  private String end;
  private int choices;

  public RatioQuestion() {}

  public RatioQuestion(String questionTitle, boolean mandatory, String start, String end,
      int choice) {
    super(questionTitle, QuestionType.RATIO, mandatory);
    setStart(start);
    setEnd(end);
    setChoices(choice);
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
