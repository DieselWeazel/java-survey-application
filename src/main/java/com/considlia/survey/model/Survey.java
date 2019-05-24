package com.considlia.survey.model;

import com.considlia.survey.model.question.Question;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "survey")
public class Survey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "survey_id")
  private Long id;

  private String title, creator, description;
  private LocalDate date = LocalDate.now();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "survey_id")
  private List<Question> questions = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;


  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "survey_id")
  private List<SurveyResponse> surveyResponseList = new ArrayList<>();

  // Related to Status Class, Status.ENUM_VALUE
  //private String currentStatus;

  public Survey() {
  }

  public Survey(String surveyTitle, String creator) {
    setTitle(surveyTitle);
    setCreator(creator);
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public List<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void addQuestion(Question question) {
    getQuestions().add(question);
  }

  public List<SurveyResponse> getSurveyResponseList() {
    return surveyResponseList;
  }

  public void setSurveyResponseList(List<SurveyResponse> surveyResponseList) {
    this.surveyResponseList = surveyResponseList;
  }

  public void moveQuestion(Question question, int moveDirection) {
    question.setPosition(question.getPosition() + moveDirection);
  }

  @Override
  public String toString() {
    return "Survey [surveyId="
        + id
        + ", surveyTitle="
        + title
        + ", creator="
        + creator
        + ", date="
        + date
        + ", questionList="
        + questions
        + "]";
  }
}
