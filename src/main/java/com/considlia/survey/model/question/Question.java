package com.considlia.survey.model.question;

import com.considlia.survey.model.answer.Answer;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import com.considlia.survey.model.QuestionType;

/**
 * Class to create questions
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "question_id")
  private Long id;

  private String title;
  private int position;
  private QuestionType questionType;
  private boolean mandatory;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "question_id")
  private Set<Answer> answerSet = new HashSet<>();

  /**
   * Constructor for Question
   */
  public Question() {}

  public Question(String questionTitle, QuestionType questionType, boolean mandatory) {
    this.title = questionTitle;
    this.mandatory = mandatory;
    this.questionType = questionType;
  }

  /**
   * Constructor for Question
   *
   * @param questionTitle - The question
   * @param position - The position in the Survey
   * @param questionType - The question type
   * @param mandatory - If the question is mandatory
   */
  public Question(String questionTitle, int position, QuestionType questionType,
      boolean mandatory) {
    this.title = questionTitle;
    this.position = position;
    this.mandatory = mandatory;
    this.questionType = questionType;
  }

  /**
   * Gets the actual question
   *
   * @return title - The question
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the actual question
   *
   * @param title - The question text
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the position of the question in the Survey
   *
   * @return The position of the question in the Survey
   */
  public int getPosition() {
    return position;
  }

  /**
   * Sets the position of the question in the Survey
   *
   * @param position - The position of the question in the Survey
   */
  public void setPosition(int position) {
    this.position = position;
  }

  /**
   * Gets the question type
   *
   * @return The question type
   */
  public QuestionType getQuestionType() {
    return questionType;
  }

  /**
   * Sets the question type
   *
   * @param questionType - The question type
   */
  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
  }

  /**
   * Gets if the question is mandatory
   *
   * @return If the question is mandatory
   */
  public boolean isMandatory() {
    return mandatory;
  }

  /**
   * Sets if the question is mandatory
   *
   * @param mandatory - If the question is mandatory
   */
  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
  }

  /**
   * Gets the id of the question
   *
   * @return The id of the question
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the Answers to this Question.
   * @return answers related to this question.
   */
  public Set<Answer> getAnswerSet() {
    return answerSet;
  }

  @Override
  public String toString() {
    return getTitle();
  }
}
