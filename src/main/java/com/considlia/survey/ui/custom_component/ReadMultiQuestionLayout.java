package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.MultiQuestion;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;

public class ReadMultiQuestionLayout extends ReadQuestionLayout {

  private RadioButtonGroup<MultiQuestionAlternative> radioButtons;
  private CheckboxGroup<MultiQuestionAlternative> checkBoxButtons;

  public ReadMultiQuestionLayout(MultiQuestion question) {
    super(question);
    if (question.getQuestionType() == QuestionType.RADIO) {
      this.radioButtons = new RadioButtonGroup<>();
      radioButtons.setItems(question.getAlternatives());
      add(radioButtons);
      radioButtons.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

    } else {
      this.checkBoxButtons = new CheckboxGroup<>();
      checkBoxButtons.setItems(question.getAlternatives());
      add(checkBoxButtons);
      checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    }
  }

  public RadioButtonGroup<MultiQuestionAlternative> getRadioButtons() {
    return radioButtons;
  }

  public CheckboxGroup<MultiQuestionAlternative> getCheckBoxButtons() {
    return checkBoxButtons;
  }

}
