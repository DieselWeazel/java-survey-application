package com.considLia.survey.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "survey")
public class Survey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "survey_id")
  private long surveyId;

  private String surveyTitle, creator;
  private Date date;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "survey_multi", joinColumns = @JoinColumn(name = "survey_id"),
      inverseJoinColumns = @JoinColumn(name = "question_id"))
  private Set<MultiQuestion> multiQuestionList;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "survey_text", joinColumns = @JoinColumn(name = "survey_id"),
      inverseJoinColumns = @JoinColumn(name = "question_id"))
  private Set<TextQuestion> textQuestionList;

  public Survey() {}

  public Survey(String surveyTitle, String creator) {
    setSurveyTitle(surveyTitle);
    setCreator(creator);
    multiQuestionList = new HashSet<>();
    textQuestionList = new HashSet<>();

  }

  public long getSurveyId() {
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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Set<MultiQuestion> getMultiQuestionList() {
    return multiQuestionList;
  }

  public void setMultiQuestionList(Set<MultiQuestion> multiQuestionList) {
    this.multiQuestionList = multiQuestionList;
  }

  public Set<TextQuestion> getTextQuestionList() {
    return textQuestionList;
  }

  public void setTextQuestionList(Set<TextQuestion> textQuestionList) {
    this.textQuestionList = textQuestionList;
  }

  @Override
  public String toString() {
    return "Survey [surveyId=" + surveyId + ", surveyTitle=" + surveyTitle + ", creator=" + creator
        + ", date=" + date + ", multiQuestionList=" + multiQuestionList + ", textQuestionList="
        + textQuestionList + "]";
  }

}
