package com.considlia.survey.model.question;

import com.considlia.survey.model.QuestionType;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "radioquestion")
public class RadioQuestion extends MultiQuestion {

  public RadioQuestion() {
  }

  public RadioQuestion(String questionTitle, boolean mandatory, List<String> stringAlternatives) {
    super(questionTitle, QuestionType.RADIO, mandatory, stringAlternatives);
  }
}
