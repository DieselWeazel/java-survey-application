package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.CheckBoxQuestion;
import com.considlia.survey.model.Question;
import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.RadioQuestion;
import com.considlia.survey.model.RatioQuestion;
import com.considlia.survey.model.TextQuestion;
import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.custom_component.question_with_button.MultiQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.QuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.RatioQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.TextQuestionWithButtons;

public class QuestionWithButtonsFactory {

  public static QuestionWithButtons create(Question question, CreateSurveyView csv) {

    if (question.getQuestionType() == QuestionType.TEXTFIELD) {
      return new TextQuestionWithButtons((TextQuestion) question, csv);
    } else if (question.getQuestionType() == QuestionType.TEXTAREA) {
      return new TextQuestionWithButtons((TextQuestion) question, csv);
    } else if (question.getQuestionType() == QuestionType.CHECKBOX) {
      return new MultiQuestionWithButtons((CheckBoxQuestion) question, csv);
    } else if (question.getQuestionType() == QuestionType.RADIO) {
      return new MultiQuestionWithButtons((RadioQuestion) question, csv);
    } else if (question.getQuestionType() == QuestionType.RATIO) {
      return new RatioQuestionWithButtons((RatioQuestion) question, csv);
    } else {
      return null;
    }

  }
}
