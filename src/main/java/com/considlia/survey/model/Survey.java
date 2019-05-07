package com.considlia.survey.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "survey")
public class Survey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "survey_id")
  private Long surveyId;

  private String surveyTitle, creator;
  private LocalDate date;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "survey_id")
  @OrderBy("position ASC")
  private Set<Question> questionList;

  public Survey() {
    questionList = new HashSet<>();
    date = LocalDate.now();
  }

  public Survey(String surveyTitle, String creator) {
    setSurveyTitle(surveyTitle);
    setCreator(creator);
    questionList = new HashSet<>();
    date = LocalDate.now();
  }

  public Long getSurveyId() {
    return surveyId;
  }

  public String getSurveyTitle() {
    return surveyTitle;
  }

  public void setSurveyTitle(String surveyTitle) {
    this.surveyTitle = surveyTitle;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Set<Question> getQuestionList() {
    return questionList;
  }

  public void setQuestionList(Set<Question> questionList) {
    this.questionList = questionList;
  }

  @Override
  public String toString() {
    return "Survey [surveyId=" + surveyId + ", surveyTitle=" + surveyTitle + ", creator=" + creator
        + ", date=" + date + ", questionList=" + questionList + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Survey survey = (Survey) o;
    return Objects.equals(surveyId, survey.surveyId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(surveyId);
  }
}
