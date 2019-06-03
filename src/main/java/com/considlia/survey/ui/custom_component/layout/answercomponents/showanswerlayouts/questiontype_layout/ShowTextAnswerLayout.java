package com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.ShowAnswerLayout;
import java.util.ArrayList;
import java.util.List;

public class ShowTextAnswerLayout extends ShowAnswerLayout {

  private List<String> allTextAnswers = new ArrayList<>();

  public ShowTextAnswerLayout(Question question,
      List<Answer> answerList) {
    super(question, answerList);
    for (Answer answer : answerList){

    }
  }
}
