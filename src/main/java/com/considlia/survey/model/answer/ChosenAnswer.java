package com.considlia.survey.model.answer;

import com.considlia.survey.model.question.MultiQuestionAlternative;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "choice_answer")
public class ChosenAnswer {

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
}
