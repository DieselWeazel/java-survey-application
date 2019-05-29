package com.considlia.survey.ui.custom_component;

import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

/**
 * Class to create the Radio buttons to choose Textfield or Textarea
 *
 */
public class CreateTextComponents extends VerticalLayout {

  private RadioButtonGroup<String> radioButtons;

  /**
   * Constructor for CreateTextComponents
   * 
   * @param csv - the SurveyView
   */
  public CreateTextComponents(CreateSurveyView csv) {

    HorizontalLayout buttonContainer = new HorizontalLayout();

    radioButtons = new RadioButtonGroup<>();
    radioButtons.setLabel("Answers in:");
    radioButtons.setItems("Textfield", "Textarea");
    radioButtons.addValueChangeListener(event -> csv.changeBtn());

    buttonContainer.add(radioButtons);
    add(label, buttonContainer);
  }

  /**
   * Gets the Radio buttons to choose Textfield or Textarea
   * 
   * 
   * @return the Buttons
   */
  public RadioButtonGroup<String> getRadioButtons() {
    return radioButtons;
  }

}
