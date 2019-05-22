package com.considlia.survey.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "checkboxquestion")
public class CheckBoxQuestion extends MultiQuestion {

  public CheckBoxQuestion() {

  }

  public CheckBoxQuestion(String questionTitle, boolean mandatory,
      List<String> stringAlternatives) {
    super(questionTitle, QuestionType.CHECKBOX, mandatory, stringAlternatives);
  }

}
