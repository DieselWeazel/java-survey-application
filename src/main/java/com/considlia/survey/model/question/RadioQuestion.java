package com.considlia.survey.model.question;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.considlia.survey.model.QuestionType;

/**
 *
 * Class to create RadioQuestion subclass to MultiQuestion
 *
 */
@Entity
@Table(name = "radioquestion")
public class RadioQuestion extends MultiQuestion {

  /**
   * Constructor for RadioQuestion
   */
  public RadioQuestion() {}

  /**
   * Constructor for RadioQuestion
   *
   * @param questionTitle - The question
   * @param mandatory - If the question is mandatory
   * @param stringAlternatives - List of the answer alternatives
   */
  public RadioQuestion(String questionTitle, boolean mandatory, List<String> stringAlternatives) {
    super(questionTitle, QuestionType.RADIO, mandatory, stringAlternatives);
  }
}
