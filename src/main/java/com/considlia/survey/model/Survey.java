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
@Table(name = "survey")
public class Survey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "survey_id")
  private Long id;

  private String title, creator;
  private LocalDate date = LocalDate.now();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "survey_id")
  @OrderBy("position ASC")
  private Set<Question> questions = new HashSet<>();

  public Survey() {}

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

  public Set<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<Question> questions) {
    this.questions = questions;
  }

  public void addQuestion(Question question) {
    getQuestions().add(question);
  }

  public void moveQuestion(Question question, int moveDirection) {
    question.setPosition(question.getPosition() + moveDirection);
  }

  @Override
  public String toString() {
    return "Survey [surveyId=" + id + ", surveyTitle=" + title + ", creator=" + creator + ", date="
        + date + ", questionList=" + questions + "]";
  }
}
