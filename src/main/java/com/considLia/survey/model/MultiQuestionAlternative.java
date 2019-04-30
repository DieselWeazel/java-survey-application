package com.considLia.survey.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "multiqalt")
public class MultiQuestionAlternative {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "alternative_id")
  private Long alternativeId;

  private int position;

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

  @Override
  public String toString() {
    return alternativeId +
            ": " + alternativeTitle;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  //  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//    MultiQuestionAlternative that = (MultiQuestionAlternative) o;
//    return alternativeId.equals(that.alternativeId) &&
//            alternativeTitle.equals(that.alternativeTitle);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(alternativeId, alternativeTitle);
//  }
}
