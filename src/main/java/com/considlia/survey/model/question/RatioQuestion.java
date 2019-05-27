package com.considlia.survey.model.question;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.considlia.survey.model.QuestionType;

@Entity
@Table(name = "ratingquestion")
public class RatioQuestion extends Question {

  private String start;
  private String end;
  private int choices;

  /**
   * Constructs a new {@link RatioQuestion}
   */
  public RatioQuestion() {}

  /**
   * Construct a {@link RatioQuestion} with a title, start, end, if it's mandatory and the number of
   * choices
   *
   * @param questionTitle
   * @param mandatory
   * @param start value of the first element
   * @param end value of the last element
   * @param choice number of alternatives
   */
  public RatioQuestion(String questionTitle, boolean mandatory, String start, String end,
      int choice) {
    super(questionTitle, QuestionType.RATIO, mandatory);
    setStart(start);
    setEnd(end);
    setChoices(choice);
  }

  /**
   * Returns the String value of local variable {@link start}
   *
   * @return String
   */
  public String getStart() {
    return start;
  }

  /**
   * Set the value of {@link String} start
   *
   * @param start new {@link String} value
   */
  public void setStart(String start) {
    this.start = start;
  }

  /**
   * Returns the String value of local variable {@link end}
   *
   * @return String
   */
  public String getEnd() {
    return end;
  }

  /**
   * Set the value of {@link String} end
   *
   * @param end new {@link String} value
   */
  public void setEnd(String end) {
    this.end = end;
  }

  /**
   * Returns the int value of local variable {@link choices}
   *
   * @return int
   */
  public int getChoices() {
    return choices;
  }

  /**
   * Set the value of {@link int} choices
   *
   * @param choices new {@link int} value
   */
  public void setChoices(int choices) {
    this.choices = choices;
  }
}
