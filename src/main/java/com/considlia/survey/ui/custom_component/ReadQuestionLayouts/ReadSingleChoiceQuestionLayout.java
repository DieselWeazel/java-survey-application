package com.considlia.survey.ui.custom_component.ReadQuestionLayouts;

import com.considlia.survey.model.answer.Answers;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.MultiQuestionAlternative;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.ReadQuestionComponent;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.ValidationException;

public class ReadSingleChoiceQuestionLayout extends ReadQuestionLayout implements
    ReadQuestionComponent {

  private RadioButtonGroup<MultiQuestionAlternative> radioButtons;

  public ReadSingleChoiceQuestionLayout(MultiQuestion question) {
    super(question);
    this.radioButtons = new RadioButtonGroup<>();
    radioButtons.setItems(question.getAlternatives());
    add(radioButtons);
    radioButtons.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
  }

  @Override
  public Answers gatherResponse() throws ValidationException {
    return null;
  }
}
