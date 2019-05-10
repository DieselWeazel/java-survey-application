package com.considlia.survey.model;

import com.considlia.survey.ui.custom_component.question_with_button.MultiQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.TextQuestionWithButtons;
import com.vaadin.flow.component.Component;

public class QuestionFactory {

  public Question createQuestion(Component component, int position) {
    if (component instanceof TextQuestionWithButtons) {
      TextQuestionWithButtons component2 = (TextQuestionWithButtons) component;
      TextQuestion question = new TextQuestion();
      question.setTitle(component2.getQuestion());
      question.setPosition(position);
      question.setMandatory(component2.isMandatory());
      return question;
    } else if (component instanceof MultiQuestionWithButtons) {
      MultiQuestionWithButtons component2 = (MultiQuestionWithButtons) component;
      MultiQuestion question = new MultiQuestion();
      question.setTitle(component2.getQuestion());
      question.setPosition(position);
      question.setMandatory(component2.isMandatory());
      question.setQuestionType(component2.getQuestionType());
      question.getAlternatives().addAll(component2.getAlternatives());
      return question;
    } else {
      return null;
    }
  }

}
