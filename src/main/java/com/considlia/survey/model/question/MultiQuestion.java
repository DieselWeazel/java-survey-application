package com.considlia.survey.model.question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import com.considlia.survey.model.QuestionType;

/**
 * Class to create MultiQuestion subclass to Question
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class MultiQuestion extends Question {

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "question_id")
  @OrderBy("position ASC")
  private Set<MultiQuestionAlternative> alternatives = new HashSet<>();

  /**
   * Constructor for MultiQuestion
   */
  public MultiQuestion() {

  }

  /**
   * Constructor for MultiQuestion
   * 
   * @param questionTitle - The question
   * @param questionType - The type of question
   * @param mandatory - If the question is mandatory
   * @param stringAlternatives - List of the answer alternatives
   */
  public MultiQuestion(String questionTitle, QuestionType questionType, boolean mandatory,
      List<String> stringAlternatives) {
    super(questionTitle, questionType, mandatory);
    updateAlternatives(stringAlternatives);
  }

  /**
   * Gets a set/list of all the answer alternatives
   * 
   * @return a set/list of all the answer alternatives
   */
  public Set<MultiQuestionAlternative> getAlternatives() {
    return alternatives;
  }

  /**
   * Sets a set/list of all the answer alternatives
   * 
   * @param alternatives - The set/list of all the answer alternatives
   */
  public void setAlternatives(Set<MultiQuestionAlternative> alternatives) {
    this.alternatives = alternatives;
  }

  /**
   * Adds a answer alternative to the list of answer alternative
   * 
   * @param mqa - an answer alternative
   */
  public void addAlternative(MultiQuestionAlternative mqa) {
    getAlternatives().add(mqa);
  }

  /**
   * Moves the answer alternative for the MulitQuestion up or down
   * 
   * @param mqa - The answer alternative
   * @param moveDirection - If the answer alternative should move up or down
   */
  public void moveAlternative(MultiQuestionAlternative mqa, int moveDirection) {
    mqa.setPosition(mqa.getPosition() + moveDirection);
  }

  /**
   * Gets a list of all the answer alternative for the question
   * 
   * @return A list of all the answer alternative
   */
  public List<String> getStringAlternatives() {

    List<MultiQuestionAlternative> alternativeList = new ArrayList<>();
    for (MultiQuestionAlternative alternative : getAlternatives()) {
      alternativeList.add(alternative);
    }
    // Sorting the list after the position index
    alternativeList.sort((MultiQuestionAlternative m1,
        MultiQuestionAlternative m2) -> m1.getPosition() - m2.getPosition());

    List<String> stringAlternatives = new ArrayList<>();
    for (MultiQuestionAlternative mqa : alternativeList) {
      stringAlternatives.add(mqa.getTitle());
    }
    return stringAlternatives;
  }

  /**
   * Updates the list of the answer alternative for the question
   * 
   * @param stringAlternatives - the list of the answer alternative
   */
  public void updateAlternatives(List<String> stringAlternatives) {
    getAlternatives().clear();
    for (int position = 0; position < stringAlternatives.size(); position++) {
      MultiQuestionAlternative alt = new MultiQuestionAlternative();
      alt.setPosition(position);
      alt.setTitle(stringAlternatives.get(position));
      addAlternative(alt);
    }
  }

  @Override
  public String toString() {
    return "MultiQuestion [alternativeList=" + alternatives + ", questionType=" + "]";
  }

}
