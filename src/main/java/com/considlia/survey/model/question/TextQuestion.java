package com.considlia.survey.model.question;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import com.considlia.survey.model.QuestionType;

/**
 * Abstract subclass of {@link Question}
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TextQuestion extends Question {

  /**
   * Constructs a new and empty {@link TextQuestion}
   */
  public TextQuestion() {

  }

  /**
   * Constructs a new {@link TextQuestion} with a questionTitle, type and if its mandatory
   *
   * @param questionTitle - String value
   * @param mandatory - boolean value
   */
  public TextQuestion(String questionTitle, QuestionType type, boolean mandatory) {
    super(questionTitle, type, mandatory);
  }
}
