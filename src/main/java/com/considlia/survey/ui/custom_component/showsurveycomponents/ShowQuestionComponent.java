package com.considlia.survey.ui.custom_component.showsurveycomponents;

import com.considlia.survey.model.answer.Answer;
import java.util.function.Consumer;
import javax.xml.bind.ValidationException;

/** GatherResponses, returns an Answer based on Question. */
public interface ShowQuestionComponent {
  void setMandatoryStatus();
  Answer gatherResponse(Consumer<Answer> consumer) throws ValidationException, com.vaadin.flow.data.binder.ValidationException;
}
