package com.considlia.survey.model.question;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.considlia.survey.model.QuestionType;

/**
 * Subclass of {@link Question} with the {@link QuestionType} set to {@link #TEXTFIELD}
 *
 * @author Andreas
 */
@Entity
@Table(name = "textfieldquestion")
public class TextFieldQuestion extends TextQuestion {

  /**
   * Constructs a new and empty {@link TextFieldQuestion}
   */
  public TextFieldQuestion() {}

  /**
   * Constructs a new {@link TextFieldQuestion} with a questionTitle and if its mandatory
   *
   * @param questionTitle - String value
   * @param mandatory - boolean value
   */
  public TextFieldQuestion(String questionTitle, boolean mandatory) {
    super(questionTitle, QuestionType.TEXTFIELD, mandatory);
  }

}
