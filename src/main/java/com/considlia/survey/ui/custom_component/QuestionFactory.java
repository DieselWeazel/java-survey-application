
package com.considlia.survey.ui.custom_component;

import java.util.List;
import com.considlia.survey.model.CheckBoxQuestion;
import com.considlia.survey.model.Question;
import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.RadioQuestion;
import com.considlia.survey.model.RatioQuestion;
import com.considlia.survey.model.TextAreaQuestion;
import com.considlia.survey.model.TextFieldQuestion;

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
