package com.considlia.survey.model;

import com.considlia.survey.model.answer.Answers;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "surveyresponses")
public class SurveyResponses {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "response_id")
  private Long id;

  private LocalDate date = LocalDate.now();
//   Can be whatever decided upon, timeSpent = date.plus();
// private LocalDate timeSpent;

   // Delete
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "response_id")
  @OrderBy("position ASC")
  private Set<Answers> answers = new HashSet<>();

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id")
  private Survey survey;

  // Boolean
  // private boolen surveyStatus;


  public SurveyResponses() {}

  public SurveyResponses(LocalDate date, Set<Answers> answers) {
    this.date = date;
    this.answers = answers;
  }

  public void addAnswer(Answers answer) {
    getAnswers().add(answer);
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
