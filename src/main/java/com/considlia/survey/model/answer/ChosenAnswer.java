package com.considlia.survey.model.answer;

import com.considlia.survey.model.question.MultiQuestionAlternative;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chosen_answer")
public class ChosenAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chosenanswer_id")
  private Long id;

  private String checkedAnswer;

  public ChosenAnswer(String checkedAnswer) {
    this.checkedAnswer = checkedAnswer;
  }

  public String getCheckedAnswer() {
    return checkedAnswer;
  }

  public void setCheckedAnswer(String checkedAnswer) {
    this.checkedAnswer = checkedAnswer;
  }

  @Override
  public String toString() {
    return "ChosenAnswer{" +
        "checkedAnswer='" + checkedAnswer + '\'' +
        '}';
  }
}
