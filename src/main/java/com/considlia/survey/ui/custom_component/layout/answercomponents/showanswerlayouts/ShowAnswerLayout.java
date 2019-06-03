package com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ShowAnswerLayout extends VerticalLayout {

  private H5 title;
  private Question question;
  private List<Answer> answerList;
  private static final Logger LOGGER = LoggerFactory.getLogger(ShowQuestionLayout.class);

  public ShowAnswerLayout(Question question, List<Answer> answerList){
    this.answerList = answerList;
    this.question = question;
    title = new H5(question.getTitle() + "type: " + question.getQuestionType());
    LOGGER.info("Loading Question: '{}'", question.getTitle());
    LOGGER.info("AnswerList size: '{}'", answerList.size());
  }
}
