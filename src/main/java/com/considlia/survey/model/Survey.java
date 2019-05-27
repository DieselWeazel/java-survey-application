package com.considlia.survey.model;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import com.considlia.survey.model.question.Question;

/**
 * Class to create a Survey
 */
@Entity
@Table(name = "survey")
// @Table(name = "survey", uniqueConstraints = @UniqueConstraint(columnNames = {"title",
// "user_id"}))
public class Survey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "survey_id")
  private Long id;

  private String title, creator, description;
  private LocalDate date = LocalDate.now();
  private SurveyStatus status;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "survey_id")
  private List<Question> questions = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "survey_id")
  private List<SurveyResponse> surveyResponseList = new ArrayList<>();

  /**
   * Constructor for Survey
   */
  public Survey() {}

  /**
   * Constructor for Survey
   *
   * @param surveyTitle
   * @param creator
   */
  public Survey(String surveyTitle, String creator) {
    setTitle(surveyTitle);
    setCreator(creator);
  }

  /**
   * Gets the Id from the database
   *
   * @return The id of the Survey from the database
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the title of the Survey
   *
   * @return The title of the Survey
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the Survey
   *
   * @param title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the Creator of the Survey
   *
   * @return The Creator of the Survey
   */
  public String getCreator() {
    return creator;
  }

  /**
   * Sets the Creator of the Survey
   *
   * @param creator
   */
  public void setCreator(String creator) {
    this.creator = creator;
  }

  /**
   * Gets the date when the Survey was created
   *
   * @return The date when the Survey was created
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Sets the date when the Survey was created
   *
   * @param date - The current date
   */
  public void setDate(LocalDate date) {
    this.date = date;
  }

  /**
   * Gets the list of questions in the Survey
   *
   * @return The list of questions in the Survey
   */
  public List<Question> getQuestions() {
    return questions;
  }

  /**
   * Sets the list of questions in the Survey
   *
   * @param questions
   */
  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }

  /**
   * Gets the Description of the Survey
   *
   * @return The description of the Survey
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the Description of the Survey
   *
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the user of the Survey
   *
   * @return user
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the User as user of the Survey
   *
   * @param user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Add a question to the Survey.
   *
   * @param question
   */
  public void addQuestion(Question question) {
    getQuestions().add(question);
  }

  /**
   * Gets the list of SurveyResponses
   *
   * @return The list of SurveyResponses
   */
  public List<SurveyResponse> getSurveyResponseList() {
    return surveyResponseList;
  }

  /**
   * Sets the list of SurveyResponses
   *
   * @param surveyResponseList - The list of SurveyResponses
   */
  public void setSurveyResponseList(List<SurveyResponse> surveyResponseList) {
    this.surveyResponseList = surveyResponseList;
  }

  /**
   * To move the question up/down in the survey to change the order of the questions
   *
   * @param question
   * @param moveDirection
   */
  public void moveQuestion(Question question, int moveDirection) {
    question.setPosition(question.getPosition() + moveDirection);
  }

  /**
   * Gets status of Survey. Can be EDITABLE, PUBLIC, PRIVATE or CLOSED
   *
   * @return The status of the Survey
   */
  public SurveyStatus getStatus() {
    return status;
  }

  /**
   *
   * Sets status of Survey. Can be EDITABLE, PUBLIC, PRIVATE or CLOSED
   *
   * @param status - The status of the Survey
   */
  public void setStatus(SurveyStatus status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Survey [surveyId=" + id + ", surveyTitle=" + title + ", creator=" + creator + ", date="
        + date + ", questionList=" + questions + "]";
  }
}
