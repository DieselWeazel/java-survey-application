package com.considlia.survey.model.question;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.considlia.survey.model.QuestionType;

/**
 * Subclass of {@link Question} with the {@link QuestionType} set to {@link #TEXTAREA}
 * 
 * @author Andreas
 */
@Entity
@Table(name = "textareaquestion")
public class TextAreaQuestion extends TextQuestion {

  /**
   * Constructs a new and empty {@link TextAreaQuestion}
   */
  public TextAreaQuestion() {}

  /**
   * Constructs a new {@link TextAreaQuestion} with a questionTitle and if its mandatory
   * 
   * @param questionTitle - String value
   * @param mandatory - boolean value
   */
  public TextAreaQuestion(String questionTitle, boolean mandatory) {
    super(questionTitle, QuestionType.TEXTAREA, mandatory);
  }

}
