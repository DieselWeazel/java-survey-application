package com.considlia.survey.model;

import com.considlia.survey.ui.custom_component.question_with_button.MultiQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.TextQuestionWithButtons;
import com.vaadin.flow.component.Component;

public class QuestionFactory {

  public Question createQuestion(Component component, int position) {
    if (component instanceof TextQuestionWithButtons) {
      TextQuestionWithButtons castComponent = (TextQuestionWithButtons) component;
      TextQuestion question = new TextQuestion();
      question.setTitle(castComponent.getQuestion());
      question.setPosition(position);
      question.setMandatory(castComponent.isMandatory());
      return question;
    } else if (component instanceof MultiQuestionWithButtons) {
      MultiQuestionWithButtons castComponent = (MultiQuestionWithButtons) component;
      MultiQuestion question = new MultiQuestion();
      question.setTitle(castComponent.getQuestion());
      question.setPosition(position);
      question.setMandatory(castComponent.isMandatory());
      question.setQuestionType(castComponent.getQuestionType());
      question.getAlternatives().addAll(castComponent.getAlternatives());
      return question;
    } else {
      return null;
    }
  }
}