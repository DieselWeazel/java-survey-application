package com.considlia.survey.model.question;

import com.considlia.survey.model.QuestionType;
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

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class MultiQuestion extends Question {

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "question_id")
  @OrderBy("position ASC")
  private Set<MultiQuestionAlternative> alternatives = new HashSet<>();

  public MultiQuestion() {

  }

  public MultiQuestion(String questionTitle, QuestionType questionType, boolean mandatory,
      List<String> stringAlternatives) {
    super(questionTitle, questionType, mandatory);
    updateAlternatives(stringAlternatives);
  }

  public Set<MultiQuestionAlternative> getAlternatives() {
    return alternatives;
  }

  public void setAlternatives(Set<MultiQuestionAlternative> alternatives) {
    this.alternatives = alternatives;
  }

  public void addAlternative(MultiQuestionAlternative mqa) {
    getAlternatives().add(mqa);
  }

  public void moveAlternative(MultiQuestionAlternative mqa, int moveDirection) {
    mqa.setPosition(mqa.getPosition() + moveDirection);
  }

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
