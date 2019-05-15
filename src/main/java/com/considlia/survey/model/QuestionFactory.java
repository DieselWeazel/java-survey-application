package com.considlia.survey.model;

import com.considlia.survey.ui.custom_component.question_with_button.MultiQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.RatioQuestionWithButtons;
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
    } else if (component instanceof RatioQuestionWithButtons) {
      RatioQuestionWithButtons castComponent = (RatioQuestionWithButtons) component;
      RatioQuestion question = new RatioQuestion();
      question.setTitle(castComponent.getQuestion());
      question.setPosition(position);
      question.setMandatory(castComponent.isMandatory());
      question.setEnd(castComponent.getEnd());
      question.setStart(castComponent.getStart());
      question.setChoices(castComponent.getChoices());
      return question;
    } else {
      return null;
    }
  }
}
