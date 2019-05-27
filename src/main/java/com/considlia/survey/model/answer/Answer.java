package com.considlia.survey.model.answer;

import com.considlia.survey.model.question.Question;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "answer_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  /** Empty Constructor */
  public Answer() {}

  /**
   * gets ID of answer.
   *
   * @return ID.
   */
  public Long getId() {
    return id;
  }

  /**
   * gets question to answer.
   *
   * @return question.
   */
  public Question getQuestion() {
    return question;
  }

  /**
   * sets Question to Answer.
   *
   * @param question related
   */
  public void setQuestion(Question question) {
    this.question = question;
  }
}
