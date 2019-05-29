package com.considlia.survey.ui.custom_component.showsurveycomponents;

import com.considlia.survey.model.Survey;
import com.considlia.survey.model.SurveyResponse;
import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.question.CheckBoxQuestion;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.question.TextAreaQuestion;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.ui.custom_component.ErrorVerificationMessageDTO;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowMultiChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowRatioQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowSingleChoiceQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowTextAreaQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout.ShowTextQuestionLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SurveyLoader
    implements ShowQuestionFactory<List<Answer>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SurveyLoader.class);

  private Survey survey;

  private List<ShowQuestionLayout> componentList = new ArrayList<>();

  private String errorMessage;

  @Override
  public VerticalLayout getSurveyLayout(Survey survey){
    this.survey=survey;
    VerticalLayout vr = new VerticalLayout();
//    for(ShowQuestionLayout showQuestionLayout : getSurveyComponentList()){
//      vr.add(showQuestionLayout);
//    }

    survey.getQuestions().stream().forEach(question ->{
      ShowQuestionLayout layout = loadQuestion(question);
      layout.setMandatoryStatus();
      componentList.add(layout);
      vr.add(layout);
    });

    return vr;
  }

  // TODO change me to a DAO Object!
  @Override
  public ErrorVerificationMessageDTO isComplete() {
//    boolean[] arr = answerList.forEach(e-> e.isCompleted());

    errorMessage = "Following Questions are mandatory: ";
    boolean isComplete = true;
    for (ShowQuestionLayout s : componentList){
      LOGGER.info("isComplete inside SurveyLoader '{}'", s.isCompleted());
      errorMessage += s.getQuestion().getTitle() + ", ";
      if (!s.isCompleted()){
        isComplete = false;
      }
    }
    errorMessage += "these have to be answered in order to submit Survey";
    LOGGER.info("errorMesage is : '{}' ", errorMessage);
    LOGGER.info("isComplete() returns: '{}', ", isComplete);
    return new ErrorVerificationMessageDTO(isComplete, errorMessage);
  }

  @Override
  public Set<Answer> getList() {
    Set<Answer> list = new HashSet<>();
    componentList.forEach(question -> {
      try {
        list.add(question.gatherResponse());
      } catch (ValidationException e) {
        //TODO delete do nothing?
        e.printStackTrace();
      }
    });
    return list;
  }
//  public List<ShowQuestionLayout> getSurveyComponentList() {
//
////    List<ShowQuestionLayout> list = new ArrayList<>();
//////    List<RequiredAnswer> requiredAnswerList = new ArrayList<>();
//////    list.addAll(survey.getQuestions().stream(question -> loadQuestion(question)));
//////    survey.getQuestions().stream().forEach(question -> question.isMandatory();
////    survey.getQuestions().stream().forEach(question -> list.add(loadQuestion(question)));
//
//
//    return answerList;
//  }


  /**
   * Implementation of Factory, Reads
   *
   * @return layout corresponding with Question. {@link ShowQuestionLayout}
   */


  private ShowQuestionLayout loadQuestion(Question question) {

    LOGGER.info("SurveyLoader: Loading a question");
    if (question instanceof CheckBoxQuestion) {
      MultiQuestion mq = (MultiQuestion) question;
      ShowMultiChoiceQuestionLayout showMultiChoiceQuestionLayout =
          new ShowMultiChoiceQuestionLayout(mq);
      LOGGER.info("SurveyLoader: Loading '{}'", mq.getTitle());
      return showMultiChoiceQuestionLayout;
    } else if (question instanceof RadioQuestion) {
      RadioQuestion radioQuestion = (RadioQuestion) question;
      ShowSingleChoiceQuestionLayout showSingleChoiceQuestionLayout =
          new ShowSingleChoiceQuestionLayout(radioQuestion);
      LOGGER.info("SurveyLoader: Loading '{}'", radioQuestion.getTitle());
      return showSingleChoiceQuestionLayout;
    } else if (question instanceof TextQuestion) {
      ShowTextQuestionLayout showTextQuestionLayout = new ShowTextQuestionLayout(question);
      LOGGER.info("SurveyLoader: Loading '{}'", question.getTitle());
      return showTextQuestionLayout;
    } else if (question instanceof TextAreaQuestion) {
      ShowTextAreaQuestionLayout showTextAreaQuestionLayout = new ShowTextAreaQuestionLayout(question);
      LOGGER.info("SurveyLoader: loading '{}'", question.getTitle());
      return showTextAreaQuestionLayout;
    } else if (question instanceof RatioQuestion) {
      RatioQuestion rq = (RatioQuestion) question;
      ShowRatioQuestionLayout showRatioQuestionLayout = new ShowRatioQuestionLayout(rq);
      LOGGER.info("SurveyLoader: Loading '{}'", rq.getTitle());
      return showRatioQuestionLayout;
    }
    throw new RuntimeException("No Layout Available for Question: " + question.getQuestionType());
  }
}
