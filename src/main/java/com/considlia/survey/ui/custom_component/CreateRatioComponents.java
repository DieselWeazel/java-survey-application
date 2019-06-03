package com.considlia.survey.ui.custom_component;

import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.notification.Notification;
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
   * @param csv CreateSurveyView to getList access to the changeBtn-method
   */
  public CreateRatioComponents(CreateSurveyView csv) {
    this.csv = csv;
    HorizontalLayout limitContainer = new HorizontalLayout();
    limitContainer.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

    lowerLimit = createTextField("Lower Limit", "The value of the first option");
    upperLimit = createTextField("Upper Limit", "The value of the last option");

    stepperField = new NumberField();
    stepperField.setLabel("Number of options");
    stepperField.getStyle().set("width", "140px");
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
   * Returns whether TextFields lowerLimit and upperLimit dosn't contain any digits.
   *
   * @return True if ther's only letters. False if not
   */
  public boolean limitsContainsOnlyLetters() {

    return !(lowerLimit.getValue().chars().anyMatch(Character::isDigit)
        || upperLimit.getValue().chars().anyMatch(Character::isDigit));
  }

  /**
   * Shows a {@link Notification} with a error message if the requirements aren't met.
   * 
   * @param txtField - {@link TextField}
   */
  public void showValidNotification(TextField txtField) {
    String errorMessage = "";
    if (txtField.getValue().trim().length() < 1 || txtField.getValue().length() >= 255) {
      errorMessage += "Can't be empty and can only contain 255 characters.";
    }
    if (txtField.getValue().chars().anyMatch(Character::isDigit)) {
      errorMessage += "Can contain any numbers. ";
    }
    if (!errorMessage.equals("")) {
      new Notification(errorMessage, 2000).open();
    }
  }

  /**
   * Construct a TextField with ValueChangeMode of EAGER and a {@link ValueChangeListener}
   *
   * @param placeholderInput the TextFields placeholder
   * @return TextField
   */
  public TextField createTextField(String placeholderInput, String label) {
    TextField txtField = new TextField();
    txtField.setMaxLength(255);
    txtField.setLabel(label);
    txtField.setPlaceholder(placeholderInput);
    txtField.setValueChangeMode(ValueChangeMode.EAGER);
    txtField.addValueChangeListener(e -> {
      showValidNotification(txtField);
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
