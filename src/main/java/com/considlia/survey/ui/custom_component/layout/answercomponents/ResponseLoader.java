package com.considlia.survey.ui.custom_component.layout.answercomponents;

import com.considlia.survey.model.Survey;
import com.considlia.survey.model.SurveyResponse;
import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.CheckBoxAnswer;
import com.considlia.survey.model.answer.CheckBoxAnswerChoice;
import com.considlia.survey.model.answer.RadioAnswer;
import com.considlia.survey.model.answer.RatioAnswer;
import com.considlia.survey.model.answer.TextAnswer;
import com.considlia.survey.model.question.CheckBoxQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.question.TextAreaQuestion;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.ui.custom_component.ErrorVerificationMessageDTO;
import com.considlia.survey.ui.custom_component.layout.LoadLayout;
import com.considlia.survey.ui.custom_component.layout.ShowQuestionFactory;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.ShowAnswerLayout;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout.ShowMultiChoiceAnswerLayout;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout.ShowRadioAnswerLayout;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout.ShowRatioAnswerLayout;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout.ShowTextAnswerLayout;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout.ShowTextAreaAnswerLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseLoader implements ShowQuestionFactory<List<SurveyResponse>>,
    LoadLayout<ShowAnswerLayout, Question> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResponseLoader.class);

  private Survey survey;


  @Override
  public VerticalLayout getSurveyLayout(Survey survey) {
    this.survey = survey;
    VerticalLayout vr = new VerticalLayout();
    survey.getQuestions().stream().forEach(question -> {
      ShowAnswerLayout showAnswerLayout = loadLayout(question);
      vr.add(showAnswerLayout);
    });
    return vr;
  }

  /**
   * For future, verify if Survey is finished, displays message explaining that Survey will be //
   * removed and finished after completion.
   * @return String explaining Survey can no longer be answered. Can be altered
   * to include a boolean telling if Survey has the amount of responses that the Survey
   * was aiming to get.
   * RETURNS NULL until then
   */
  @Override
  public ErrorVerificationMessageDTO isComplete() {
    return null;
  }

  /**
   * For future, this method could gather the responses and format them into a PDF/Word Document
   * @return list of responses (right now NULL)
   */
  @Override
  public List<SurveyResponse> getList() {
    return null;
  }

  @Override
  public ShowAnswerLayout loadLayout(Question question) {
    LOGGER.info("ReponseLoader: Loading a question, type '{}'", question.getQuestionType());

    if (question instanceof CheckBoxQuestion) {

      List<CheckBoxAnswerChoice> list = new ArrayList<>();

      for (Answer answer : question.getAnswerSet()){
        if (answer instanceof CheckBoxAnswer){
          for (CheckBoxAnswerChoice multiAnswerChoice : ((CheckBoxAnswer) answer).getMultiAnswerChoiceSet()){
            list.add(multiAnswerChoice);
          }
        }
      }
      LOGGER.info("ReponseLoader: Loading '{}'", question.getTitle());
      return new ShowMultiChoiceAnswerLayout(question, list);

    } else if (question instanceof RadioQuestion) {

      List<RadioAnswer> list = new ArrayList<>();

      for (Answer answer : question.getAnswerSet()){
        list.add((RadioAnswer) answer);
      }
      LOGGER.info("ReponseLoader: Loading '{}'", question.getTitle());
      return new ShowRadioAnswerLayout(question, list);

    } else if (question instanceof TextQuestion) {

      List<TextAnswer> list = new ArrayList<>();

      for (Answer answer : question.getAnswerSet()){
        list.add((TextAnswer) answer);
      }
      LOGGER.info("ReponseLoader: Loading '{}'", question.getTitle());
      return new ShowTextAnswerLayout(question, list);

    } else if (question instanceof TextAreaQuestion) {

      List<TextAnswer> list = new ArrayList<>();

      for (Answer answer : question.getAnswerSet()){
        list.add((TextAnswer) answer);
      }
      LOGGER.info("ReponseLoader: loading '{}'", question.getTitle());
      return new ShowTextAreaAnswerLayout(question, list);
    } else if (question instanceof RatioQuestion) {

      List<RatioAnswer> list = new ArrayList<>();

      for (Answer answer : question.getAnswerSet()){
        list.add((RatioAnswer) answer);
      }
      LOGGER.info("ReponseLoader: Loading '{}'", question.getTitle());
      return new ShowRatioAnswerLayout(question, list);
    }
    throw new RuntimeException("No Layout Available for Question: " + question.getQuestionType());
  }

}
