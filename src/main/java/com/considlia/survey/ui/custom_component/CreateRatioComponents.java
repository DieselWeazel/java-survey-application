package com.considlia.survey.ui.custom_component;

import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class CreateRatioComponents extends VerticalLayout {

  private TextField lowerLimit, upperLimit;
  private NumberField stepperField;

  public CreateRatioComponents(CreateSurveyView csv) {
    HorizontalLayout limitContainer = new HorizontalLayout();

    lowerLimit = new TextField();
    lowerLimit.setPlaceholder("Lower Limit");
    lowerLimit.setValueChangeMode(ValueChangeMode.EAGER);
    lowerLimit.addValueChangeListener(
        e -> {
          csv.changeBtn();
        });

    upperLimit = new TextField();
    upperLimit.setPlaceholder("Upper Limit");
    upperLimit.setValueChangeMode(ValueChangeMode.EAGER);
    upperLimit.addValueChangeListener(
        e -> {
          csv.changeBtn();
        });

    stepperField = new NumberField();
    stepperField.setValue(5d);
    stepperField.setMin(1);
    stepperField.setMax(10);
    stepperField.setHasControls(true);

    limitContainer.add(lowerLimit, upperLimit, stepperField);
    add(limitContainer);
  }

  public boolean isLimitEmpty() {
    return (lowerLimit.isEmpty() || upperLimit.isEmpty());
  }

  public void resetParts() {
    lowerLimit.clear();
    upperLimit.clear();
    stepperField.clear();
  }

  public String getLowerLimit() {
    return lowerLimit.getValue();
  }

  public String getUperLimit() {
    return upperLimit.getValue();
  }

  public int getStepperValue() {
    return (int) Math.round(stepperField.getValue());
  }
}
