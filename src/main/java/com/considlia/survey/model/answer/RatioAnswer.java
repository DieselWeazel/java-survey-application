package com.considlia.survey.model.answer;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * Child of inheritance from {@link Answer}
 */
@Entity
@Table(name = "ratio_answer")
public class RatioAnswer extends Answer {

  private String ratioAnswer;

  /**
   * Gets RatioAnswer
   *
   * @return the answered number within the ratio of {@link
   *     com.considlia.survey.model.question.RatioQuestion}
   */
  public String getRatioAnswer() {
    return ratioAnswer;
  }

  /**
   * Sets the RatioAnswer
   *
   * @param ratioAnswer within the ratio of {@link
   *     com.considlia.survey.model.question.RatioQuestion}
   */
  public void setRatioAnswer(String ratioAnswer) {
    this.ratioAnswer = ratioAnswer;
  }
}
