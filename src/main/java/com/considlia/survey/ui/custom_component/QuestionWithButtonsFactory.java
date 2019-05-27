package com.considlia.survey.ui.custom_component;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.question.CheckBoxQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.custom_component.question_with_button.MultiQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.QuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.RatioQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.TextQuestionWithButtons;

/**
 * Contains method to create and return a subclass of {@link QuestionWithButtons}
 * 
 *
 */
public class QuestionWithButtonsFactory {

  /**
   * Returns a subclass of {@link QuestionWithButtons}.
   * <p>
   * {@link Question#getQuestionType()} specifies which class is created
   * 
   * @param question - {@link Question}
   * @param csv - {@link CreateSurveyView}
   * @throws IllegalIdentifierException
   * @return - returns a subclass of {@link QuestionWithButtons}
   */
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
      throw new IllegalIdentifierException("Invalid QuestionType");
    }
  }
}
