package com.considlia.survey.custom_component;

import com.considlia.survey.model.MultiQuestion;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;

public class ReadMultiQuestionLayout extends VerticalLayout {

  private RadioButtonGroup<MultiQuestionAlternative> radioButtons;
  private CheckboxGroup<MultiQuestionAlternative> checkBoxButtons;

  private H5 title;

  public ReadMultiQuestionLayout(MultiQuestion question) {
    this.title = new H5(question.getTitle() + (question.isMandatory() ? "*" : ""));

    add(title);

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
}
