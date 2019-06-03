package com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.RadioAnswer;
import com.considlia.survey.model.answer.RatioAnswer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.ShowAnswerLayout;
import com.vaadin.flow.component.html.H5;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShowRadioAnswerLayout extends ShowAnswerLayout {

  private List<String> radioAnswerList = new ArrayList<>();

  public ShowRadioAnswerLayout(Question question,
      List<RadioAnswer> answerList) {
    super(question);

    for (RadioAnswer answer : answerList){
      getLOGGER().info("Adding answer '{}'", answer.getChosenAnswer());
      radioAnswerList.add(answer.getChosenAnswer());
      add(new H5(answer.getChosenAnswer() + ", "));

    }
  }
}
