package com.considlia.survey.model;

import java.util.Objects;
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
  private int position;

  public MultiQuestionAlternative() {
  }

  public MultiQuestionAlternative(String questionTitle) {
    this.alternativeTitle = questionTitle;
  }

  public String getAlternativeTitle() {
    return alternativeTitle;
  }

  public void setAlternativeTitle(String alternativeTitle) {
    this.alternativeTitle = alternativeTitle;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public String toString() {
    return position + ": " + alternativeTitle;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultiQuestionAlternative that = (MultiQuestionAlternative) o;
    return alternativeId.equals(that.alternativeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alternativeId);
  }
}
