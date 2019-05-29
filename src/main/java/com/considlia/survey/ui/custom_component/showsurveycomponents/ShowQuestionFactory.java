package com.considlia.survey.ui.custom_component.showsurveycomponents;

import com.considlia.survey.model.Survey;
import com.considlia.survey.model.question.Question;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.List;

/**
 * Factory for loading Questions, can be inherited for use with Loading anything that returns a
 * Question.
 *
 * @param <T> Question, or Answer.
 */
public interface ShowQuestionFactory<T> {

  VerticalLayout getSurveyLayout(Survey survey);
  boolean isComplete();
  T getEntity();

}
