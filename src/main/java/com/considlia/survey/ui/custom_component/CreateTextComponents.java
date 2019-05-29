package com.considlia.survey.ui.custom_component;

import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

/**
 * Class to create the Radio buttons to choose Textfield or Textarea in question type
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
    radioButtons.setLabel("Answers in: ");
    radioButtons.setItems("Textfield", "Textarea");
    radioButtons.setValue("Textfield");
    radioButtons.addValueChangeListener(event -> csv.changeBtn());

    buttonContainer.add(radioButtons);
    add(buttonContainer);
  }

  /**
   * Gets the Radio buttons to choose Textfield or Textarea
   * 
   * @return the Buttons
   */
  public RadioButtonGroup<String> getRadioButtons() {
    return radioButtons;
  }

}
