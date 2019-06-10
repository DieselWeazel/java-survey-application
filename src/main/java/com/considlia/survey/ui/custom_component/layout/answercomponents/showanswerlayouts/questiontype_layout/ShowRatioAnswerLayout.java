package com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.RatioAnswer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.ShowAnswerLayout;
import com.vaadin.flow.component.html.H5;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShowRatioAnswerLayout extends ShowAnswerLayout {

  private List<String> allChosenRatioAnswers = new ArrayList<>();

  public ShowRatioAnswerLayout(Question question,
      List<RatioAnswer> answerList) {
    super(question);
    for (RatioAnswer answer : answerList){

      LOGGER.info("Adding answer '{}'", answer.getRatioAnswer());

      allChosenRatioAnswers.add(answer.getRatioAnswer());
      add(new H5(answer.getRatioAnswer() + ", "));

    }
  }
}
