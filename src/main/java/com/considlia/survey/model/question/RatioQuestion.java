package com.considlia.survey.model.question;

import com.considlia.survey.model.question.Question;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import com.considlia.survey.ui.custom_component.QuestionType;

@Entity
@Table(name = "ratingquestion")
public class RatioQuestion extends Question {

  private String start;
  private String end;
  private int choices;

  public RatioQuestion() {}

  public RatioQuestion(String questionTitle, int position, QuestionType questionType,
      boolean mandatory, String start, String end, int choices) {
    super(questionTitle, position, questionType, mandatory);

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
