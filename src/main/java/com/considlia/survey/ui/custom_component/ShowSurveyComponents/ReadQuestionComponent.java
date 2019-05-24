package com.considlia.survey.ui.custom_component.ShowSurveyComponents;

import com.considlia.survey.model.answer.Answer;
import com.vaadin.flow.data.binder.ValidationException;

/**
 * GatherResponses, returns an Answer based on Question.
 */
public interface ReadQuestionComponent {
  Answer gatherResponse() throws ValidationException;
}
