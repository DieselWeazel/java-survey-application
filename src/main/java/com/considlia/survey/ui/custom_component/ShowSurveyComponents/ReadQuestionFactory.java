package com.considlia.survey.ui.custom_component.ShowSurveyComponents;

import com.considlia.survey.model.Survey;
import com.considlia.survey.model.question.Question;

/**
 * Factory for loading Questions, can be inherited for use with Loading anything that returns a Question.
 * @param <T> Question, or Answer.
 */
public interface ReadQuestionFactory<T> {

  T loadQuestion(Question question);

}
