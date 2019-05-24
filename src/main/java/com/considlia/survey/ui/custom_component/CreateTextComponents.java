package com.considlia.survey.ui.custom_component;

import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

public class CreateTextComponents extends VerticalLayout {

  private RadioButtonGroup<String> radioButtons;

  public CreateTextComponents(CreateSurveyView csv) {

    HorizontalLayout buttonContainer = new HorizontalLayout();
    Label label = new Label();
    label.add("Answer in: ");

    radioButtons = new RadioButtonGroup<>();
    radioButtons.setItems("Textfield", "Textarea");
    radioButtons.addValueChangeListener(event -> csv.changeBtn());

    buttonContainer.add(radioButtons);
    add(label, buttonContainer);
  }

  public RadioButtonGroup<String> getRadioButtons() {
    return radioButtons;
  }

  public void clearRadioButtons() {
    radioButtons.clear();
  }

}
