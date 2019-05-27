package com.considlia.survey.model.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class to create MultiQuestionAlternative, the the answer alternative for the multiquestions
 */
@Entity
@Table(name = "multiqalt")
public class MultiQuestionAlternative {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "alternative_id")
  private Long id;

  private String title;
  private int position;

  /**
   * Constructor for MultiQuestionAlternative
   */
  public MultiQuestionAlternative() {}

  /**
   * Constructor for MultiQuestionAlternative
   * 
   * @param questionTitle - The question
   */
  public MultiQuestionAlternative(String questionTitle) {
    this.title = questionTitle;
  }

  /**
   * Gets the alternative
   * 
   * @return - The alternative
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the alternative
   * 
   * @param title - The alternative
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the position of the alternative
   * 
   * @return the position
   */
  public int getPosition() {
    return position;
  }

  /**
   * Sets the position of the alternative
   * 
   * @param position - The position
   */
  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public String toString() {
    return title;
  }
}
