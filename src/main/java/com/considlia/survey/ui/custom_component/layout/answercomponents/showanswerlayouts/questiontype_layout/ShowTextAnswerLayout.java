package com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout;

import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.TextAnswer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.ShowAnswerLayout;
import com.vaadin.flow.component.html.H5;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShowTextAnswerLayout extends ShowAnswerLayout {

  private List<String> allTextAnswers = new ArrayList<>();

  public ShowTextAnswerLayout(Question question,
      List<TextAnswer> answerList) {
    super(question);
    for (TextAnswer answer : answerList){
      if (answer.getQuestion().getQuestionType() == QuestionType.TEXTFIELD) {
        allTextAnswers.add(answer.getTextAnswer());
        add(new H5(answer.getTextAnswer()) + ", ");
      }
    }

  }
}
