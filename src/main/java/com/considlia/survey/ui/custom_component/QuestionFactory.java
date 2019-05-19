
package com.considlia.survey.ui.custom_component;

import java.util.List;
import com.considlia.survey.model.MultiQuestion;
import com.considlia.survey.model.Question;
import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.RatioQuestion;
import com.considlia.survey.model.TextQuestion;
import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.custom_component.question_with_button.MultiQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.QuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.RatioQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.TextQuestionWithButtons;

public class QuestionFactory {

  public static QuestionWithButtons createQuestion(String question, QuestionType type,
      boolean mandatory, CreateSurveyView csv) {
    TextQuestion textQuestion = new TextQuestion();
    setCommonVariables(textQuestion, question, type, mandatory);

    return new TextQuestionWithButtons(textQuestion, csv);
  }

  public static QuestionWithButtons createQuestion(String question, QuestionType type,
      boolean mandatory, List<String> stringAlternatives, CreateSurveyView csv) {
    MultiQuestion multiQuestion = new MultiQuestion();
    setCommonVariables(multiQuestion, question, type, mandatory);

    return new MultiQuestionWithButtons(multiQuestion, csv, stringAlternatives);
  }

  public static QuestionWithButtons createQuestion(String question, QuestionType type,
      boolean mandatory, String start, String end, int nrOfChoices, CreateSurveyView csv) {
    RatioQuestion ratioQuestion = new RatioQuestion();
    setCommonVariables(ratioQuestion, question, type, mandatory);
    ratioQuestion.setChoices(nrOfChoices);
    ratioQuestion.setEnd(end);
    ratioQuestion.setStart(start);

    return new RatioQuestionWithButtons(ratioQuestion, csv);
  }

  private static void setCommonVariables(Question question, String title, QuestionType type,
      boolean mandatory) {
    question.setTitle(title);
    question.setQuestionType(type);
    question.setMandatory(mandatory);
  }
}
