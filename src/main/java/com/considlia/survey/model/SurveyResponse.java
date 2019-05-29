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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.security.SecurityUtils;

/**
 * SurveyResponse of parent entity {@link Survey} Used for storing each response with answers of
 * {@link com.considlia.survey.model.question.Question}
 */
@Entity
public class SurveyResponse {

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

  /** Empty Constructor */
  public SurveyResponse() {}

  /**
   * Constructs a SurveyResponse with date and list of answers.
   *
   * @param date is the date of Survey being answered.
   * @param answers is the answers to the questions of {@link Survey}
   */
  public SurveyResponse(LocalDate date, Set<Answer> answers) {
    this.date = date;
    this.answers = answers;
  }

  /**
   * Adds an Answer to SurveyResponse list.
   *
   * @param answer is the Answer of {@link com.considlia.survey.model.question.Question}
   */
  public void addAnswer(Answer answer) {
    getAnswers().add(answer);
  }

  /**
   * Gets Date of SurveyResponse creation
   *
   * @return date of SurveyResponse creation.
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Sets Date of SurveyResponse creation.
   *
   * @param date of SurveyResponse creation.
   */
  public void setDate(LocalDate date) {
    this.date = date;
  }

  /**
   * Gets list of Answers.
   *
   * @return list of Answers to {@link com.considlia.survey.model.question.Question} belonging to
   *         {@link Survey}
   */
  public Set<Answer> getAnswers() {
    return answers;
  }

  /**
   * Sets Answers within SurveyResponse.
   *
   * @param answers list of Answers to {@link com.considlia.survey.model.question.Question}
   *        belonging to {@link Survey}
   */
  public void setAnswers(Set<Answer> answers) {
    this.answers = answers;
  }

  /**
   * Gets the ID of SurveyResponse.
   *
   * @return the ID of SurveyResponse.
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the User that responded to Survey.
   *
   * @return User that responded to Survey. If no user is present, SurveyResponse is anonymous.
   */
  public User getUser() {
    return user;
  }

  /**
   * Get the Survey of origin for SurveyResponse
   * 
   * @return Survey that owns this SurveyResponse.
   */
  public Survey getSurvey() {
    return survey;
  }

  /**
   * Set the Survey of origin to this response.
   * 
   * @param survey
   */
  public void setSurvey(Survey survey) {
    this.survey = survey;
  }

  /**
   * Sets the User that responded to Survey
   *
   * @param user that responded to Survey.
   */


  public void setUser(User user) {
    if (SecurityUtils.isUserLoggedIn()) {
      this.user = user;
    } else {
      throw new RuntimeException(
          "User is not signed in, setting a User for SurveyResponse Entity not possible.");
    }
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }


}
