package com.considlia.survey.ui.custom_component.ShowSurveyComponents;

import com.considlia.survey.model.answer.Answers;
import com.considlia.survey.model.question.Question;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.function.Consumer;

/**
 * GatherResponses, returns an Answer based on Question.
 */
public interface ReadQuestionComponent {
  Answers gatherResponse() throws ValidationException;
}
