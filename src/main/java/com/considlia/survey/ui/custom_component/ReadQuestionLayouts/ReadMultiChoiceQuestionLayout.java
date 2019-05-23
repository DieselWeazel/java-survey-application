package com.considlia.survey.ui.custom_component.ReadQuestionLayouts;

import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.answer.Answers;
import com.considlia.survey.model.answer.MultiChoiceAnswer;
import com.considlia.survey.model.answer.TextAnswer;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.MultiQuestionAlternative;
import com.considlia.survey.ui.custom_component.ReadQuestionComponent;
import com.considlia.survey.ui.custom_component.ReadQuestionLayouts.ReadQuestionLayout;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class ReadMultiChoiceQuestionLayout extends ReadQuestionLayout implements
    ReadQuestionComponent {

  private CheckboxGroup<MultiQuestionAlternative> checkBoxButtons;
  private MultiChoiceAnswer multiChoiceAnswer;
  private Binder<MultiChoiceAnswer> binder;

  public ReadMultiChoiceQuestionLayout(MultiQuestion question) {
    super(question);
      this.checkBoxButtons = new CheckboxGroup<>();
      checkBoxButtons.setItems(question.getAlternatives());
      add(checkBoxButtons);
      checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    }

  public CheckboxGroup<MultiQuestionAlternative> getCheckBoxButtons() {
    return checkBoxButtons;
  }

  @Override
  public Answers gatherResponse() throws ValidationException {
    return null;
  }
}
