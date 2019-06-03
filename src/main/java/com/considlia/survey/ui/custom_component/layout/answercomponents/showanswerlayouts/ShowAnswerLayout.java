package com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.layout.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ShowAnswerLayout extends VerticalLayout {

  private H4 title;
  private Question question;
  private static final Logger LOGGER = LoggerFactory.getLogger(ShowQuestionLayout.class);

  public ShowAnswerLayout(Question question){
    this.question = question;
    title = new H4(question.getTitle() + ", type: " + question.getQuestionType());
    LOGGER.info("Loading Question: '{}'", question.getTitle());
    add(title);
  }

  public static Logger getLOGGER() {
    return LOGGER;
  }
}
