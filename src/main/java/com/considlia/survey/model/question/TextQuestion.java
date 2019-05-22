package com.considlia.survey.model.question;

import com.considlia.survey.model.QuestionType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class TextQuestion extends Question {

  public TextQuestion() {

  }

  public TextQuestion(String questionTitle, QuestionType type, boolean mandatory) {
    super(questionTitle, type, mandatory);
  }
}
