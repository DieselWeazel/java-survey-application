package com.considLia.survey.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Survey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long surveyId;

  private String surveyTitle, creator;
  private Date date;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Question> questionList;

  public Survey() {}

  public Survey(String surveyTitle, String creator) {
    setSurveyTitle(surveyTitle);
    setCreator(creator);
    setQuestionList(new ArrayList<>());

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

  public List<Question> getQuestionList() {
    return questionList;
  }

  public void setQuestionList(List<Question> questionList) {
    this.questionList = questionList;
  }

  @Override
  public String toString() {
    return "Survey [surveyId=" + surveyId + ", surveyTitle=" + surveyTitle + ", creator=" + creator
        + ", date=" + date + ", questionList=" + questionList + "]";
  }


}
