package com.considlia.survey.model;

import com.considlia.survey.ui.custom_component.QuestionType;

public class QuestionFactory {

  public Question createQuestion(String questionTitle, QuestionType questionType,
      boolean mandatory) {
    if (questionType == QuestionType.TEXTFIELD) {
      TextQuestion question = new TextQuestion();
      question.setTitle(questionTitle);
      question.setMandatory(mandatory);
      question.setQuestionType(questionType);
      return question;
    } else if (questionType == QuestionType.RADIO || questionType == QuestionType.CHECKBOX) {
      MultiQuestion question = new MultiQuestion();
      question.setTitle(questionTitle);
      question.setMandatory(mandatory);
      question.setQuestionType(questionType);
      return question;
    } else if (true) {
      return null;
      // Work to do, dont have RatioQuestionWithButtons yet

      // RatioQuestionWithButtons castComponent = (RatioQuestionWithButtons) component;
      // RatioQuestion question = new RatioQuestion();
      // question.setTitle(castComponent.getQuestion());
      // question.setPosition(position);
      // question.setMandatory(castComponent.isMandatory());
      // question.setEnd(end);
      // question.setStart(start);
      // question.setChoices(choices);

    } else {
      return null;
    }
  }
}
