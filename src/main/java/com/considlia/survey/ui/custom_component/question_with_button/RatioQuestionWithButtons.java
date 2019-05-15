package com.considlia.survey.ui.custom_component.question_with_button;

import java.util.ArrayList;
import java.util.List;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;

public class RatioQuestionWithButtons extends QuestionWithButtons {

  // string string int för pluppar

  private String start, end;
  private int choices;
  private RadioButtonGroup<String> radioOptions;
  private List<String> options = new ArrayList<>();

  public RatioQuestionWithButtons(String question, CreateSurveyView survey, boolean mandatory,
      String start, String end, int choices) {
    super(question, survey, mandatory);

    setStart(start);
    setEnd(end);
    setChoices(choices);

    for (int i = 1; i <= choices; i++) {
      if (i == 1) {
        options.add(Integer.toString(i) + " " + start);
      } else if (i == choices) {
        options.add(Integer.toString(i) + " " + end);
      } else {
        options.add(Integer.toString(i));
      }
    }

    radioOptions = new RadioButtonGroup<>();
    radioOptions.setItems(options);
    radioOptions.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
    add(radioOptions);
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public int getChoices() {
    return choices;
  }

  public void setChoices(int choices) {
    this.choices = choices;
  }

}
