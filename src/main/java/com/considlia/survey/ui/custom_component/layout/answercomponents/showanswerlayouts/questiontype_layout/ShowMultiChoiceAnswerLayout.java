package com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.CheckBoxAnswer;
import com.considlia.survey.model.answer.CheckBoxAnswerChoice;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.ShowAnswerLayout;
import com.vaadin.flow.component.html.H5;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShowMultiChoiceAnswerLayout extends ShowAnswerLayout {

  private List<String> allChosenAnswers = new ArrayList<>();

  public ShowMultiChoiceAnswerLayout(Question question,
      List<CheckBoxAnswerChoice> answerList) {
    super(question);
    for (CheckBoxAnswerChoice answer : answerList){
      LOGGER.info("Adding answer '{}'", answer.getCheckedAnswer());
      allChosenAnswers.add(answer.getCheckedAnswer());
      add(new H5(answer.getCheckedAnswer() + ", "));

    }
  }
}
