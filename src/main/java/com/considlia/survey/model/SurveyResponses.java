package com.considlia.survey.model;

import com.considlia.survey.model.answer.Answer;
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
import javax.persistence.Table;

/**
 * SurveyResponse of parent entity {@link Survey}
 * Used for storing each response with answers of {@link com.considlia.survey.model.question.Question}
 */
@Entity
@Table(name = "surveyresponses")
public class SurveyResponses {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "response_id")
  private Long id;

  private LocalDate date = LocalDate.now();

  // Time spent on Survey
  private Long time;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "response_id")
  private Set<Answer> answers = new HashSet<>();

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id")
  private Survey survey;

  // Boolean
  // private boolen surveyStatus;

  /**
   * Empty Constructor
   */
  public SurveyResponses() {
  }

  /**
   *
   * @param date
   * @param answers
   */
  public SurveyResponses(LocalDate date, Set<Answer> answers) {
    this.date = date;
    this.answers = answers;
  }

  public void addAnswer(Answer answer) {
    getAnswers().add(answer);
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Set<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(Set<Answer> answers) {
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
