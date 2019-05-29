package com.considlia.survey.ui.custom_component;

import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class CreateRatioComponents extends VerticalLayout {

  private CreateSurveyView csv;
  private TextField lowerLimit, upperLimit;
  private NumberField stepperField;

  /**
   * Constructs a VerticalLayout containing a HorizontalLayout with then contains two TextFields and
   * one NumberField
   *
   * @param csv CreateSurveyView to getEntity access to the changeBtn-method
   */
  public CreateRatioComponents(CreateSurveyView csv) {
    this.csv = csv;
    HorizontalLayout limitContainer = new HorizontalLayout();

    lowerLimit = createTextField("Lower Limit");
    upperLimit = createTextField("Upper Limit");

    stepperField = new NumberField();
    stepperField.setValue(5d);
    stepperField.setMin(2);
    stepperField.setMax(10);
    stepperField.setHasControls(true);

    limitContainer.add(lowerLimit, upperLimit, stepperField);
    add(limitContainer);
  }

  /**
   * Returns whether TextFields lowerLimit and upperLimit is considered to be empty.
   *
   * @return True if either is empty. False if not
   */
  public boolean isLimitEmpty() {
    return (lowerLimit.isEmpty() || upperLimit.isEmpty());
  }

  /**
   * Construct a TextField with ValueChangeMode of EAGER and a {@link ValueChangeListener}
   *
   * @param placeholderInput the TextFields placeholder
   * @return TextField
   */
  public TextField createTextField(String placeholderInput) {
    TextField txtField = new TextField();
    txtField.setPlaceholder(placeholderInput);
    txtField.setValueChangeMode(ValueChangeMode.EAGER);
    txtField.addValueChangeListener(e -> {
      csv.changeBtn();
    });
    return txtField;
  }

  /**
   * Clears the value of all TextFields and NumberField from this class
   */
  public void resetParts() {
    lowerLimit.clear();
    upperLimit.clear();
    stepperField.clear();
  }

  /**
   * Return the value of lowerLimit-TextField
   *
   * @return String value
   */
  public String getLowerLimit() {
    return lowerLimit.getValue();
  }

  /**
   * Return the value of uperLimit-TextField
   *
   * @return String value
   */
  public String getUperLimit() {
    return upperLimit.getValue();
  }

  /**
   * Return the value of stepperField-TextField
   *
   * @return int value
   */
  public int getStepperValue() {
    return (int) Math.round(stepperField.getValue());
  }

}
