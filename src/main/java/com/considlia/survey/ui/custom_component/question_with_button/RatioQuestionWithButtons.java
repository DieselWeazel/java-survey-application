package com.considlia.survey.ui.custom_component.question_with_button;

import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;

public class RatioQuestionWithButtons extends QuestionWithButtons {

  // string string int för pluppar

  private TextField lowerLimit, upperLimit;
  private RadioButtonGroup<String> radioOptions;

  public RatioQuestionWithButtons(String question, CreateSurveyView survey, boolean mandatory) {
    super(question, survey, mandatory);

    // // limits
    // HorizontalLayout limitOptios = new HorizontalLayout();
    // lowerLimit = new TextField();
    // lowerLimit.setPlaceholder("Lower Limit");
    //
    // upperLimit = new TextField();
    // upperLimit.setPlaceholder("Upper Limit");
    // limitOptios.add(lowerLimit, upperLimit);
    //
    // // radio
    // radioOptions = new RadioButtonGroup<>();
    // radioOptions.setItems("1 " + lowerLimit, "2", "3", "4", "5 " + upperLimit);
    //
    // // radioOptions.getStyle().set("display", "inline");
    // add(limitOptios);

    Notification hej = new Notification("fuck");
    hej.open();
  }

}
