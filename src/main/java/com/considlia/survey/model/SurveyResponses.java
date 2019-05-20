package com.considlia.survey.model;

import java.time.LocalDate;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "surveyresponses")
public class SurveyResponses {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "response_id")
  private Long id;

  // delete
  private Long surveyId;

  private LocalDate date = LocalDate.now();
  // Can be whatever decided upon, timeSpent = date.plus();
// private LocalDate timeSpent;

   // Delete
  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "response_id")
  @OrderBy("question_question_id ASC")
  private Set<Answers> answers = new HashSet<>();

//  private Set<Answers> answers = new HashSet<>();


  // Boolean
  // private boolen surveyStatus;


  public SurveyResponses() {}

  public SurveyResponses(Long surveyId, LocalDate date, Set<Answers> answers) {
    this.surveyId = surveyId;
    this.date = date;
    this.answers = answers;
  }

  public void addAnswer(Answers answer) {
    getAnswers().add(answer);
  }

  public Long getSurveyId() {
    return surveyId;
  }

  public void setSurveyId(Long surveyId) {
    this.surveyId = surveyId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Set<Answers> getAnswers() {
    return answers;
  }

  public void setAnswers(Set<Answers> answers) {
    this.answers = answers;
  }

  public Long getId() {
    return id;
  }
}
