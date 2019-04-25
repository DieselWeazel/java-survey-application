package com.considLia.survey.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "textquestion")
public class TextQuestion extends Question {

  public TextQuestion() {
    super();
  }

  @Override
  public Set<MultiQuestionAlternative> getAlternativeList() {
    return null;
  }

  @Override
  public void setAlternativeList(Set<MultiQuestionAlternative> alternativeList) {}

}
