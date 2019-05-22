package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.question.CheckBoxQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.question.TextAreaQuestion;
import com.considlia.survey.model.question.TextFieldQuestion;
import java.util.List;

public class QuestionFactory {

  public static Question createQuestion(QuestionType type, String question, boolean mandatory,
      List<String> stringAlternatives, CreateRatioComponents cr) {

    if (type == QuestionType.TEXTFIELD) {
      return new TextFieldQuestion(question, mandatory);
    } else if (type == QuestionType.TEXTAREA) {

      return new TextAreaQuestion(question, mandatory);
    } else if (type == QuestionType.RADIO) {

      return new RadioQuestion(question, mandatory, stringAlternatives);
    } else if (type == QuestionType.CHECKBOX) {

      return new CheckBoxQuestion(question, mandatory, stringAlternatives);
    } else if (type == QuestionType.RATIO) {
      return new RatioQuestion(question, mandatory, cr.getLowerLimit(), cr.getUperLimit(),
          cr.getStepperValue());
    } else {

      return null;
    }
  }
}
