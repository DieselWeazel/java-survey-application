package com.considlia.survey.ui.custom_component.showsurveycomponents;

import com.considlia.survey.model.answer.Answer;
import com.vaadin.flow.data.binder.ValidationException;

/** GatherResponses, returns an Answer based on Question. */
public interface ShowQuestionComponent {
  Answer gatherResponse() throws ValidationException;
}
