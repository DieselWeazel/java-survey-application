package com.considLia.survey.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "multiqalt")
public class MultiQuestionAlternative {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "alternative_id")
  private Long alternativeId;

  private String alternativeTitle;

  public MultiQuestionAlternative() {}

  public MultiQuestionAlternative(String questionTitle) {
    this.alternativeTitle = questionTitle;
  }

  public String getAlternativeTitle() {
    return alternativeTitle;
  }

  public void setAlternativeTitle(String alternativeTitle) {
    this.alternativeTitle = alternativeTitle;
  }
}
