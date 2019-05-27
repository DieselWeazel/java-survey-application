package com.considlia.survey.model.question;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.considlia.survey.model.QuestionType;

/**
 *
 * Class to create CheckBoxQuestions subclass to MultiQuestion
 *
 */
@Entity
@Table(name = "checkboxquestion")
public class CheckBoxQuestion extends MultiQuestion {

  /**
   * Constructor for CheckBoxQuestion
   */
  public CheckBoxQuestion() {

  }

  /**
   * Constructor for CheckBoxQuestion
   *
   * @param questionTitle - The qeustion
   * @param mandatory - If the question is mandatory
   * @param stringAlternatives - List of the answer alternatives
   */
  public CheckBoxQuestion(String questionTitle, boolean mandatory,
      List<String> stringAlternatives) {
    super(questionTitle, QuestionType.CHECKBOX, mandatory, stringAlternatives);
  }

}
