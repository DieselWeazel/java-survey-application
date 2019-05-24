package com.considlia.survey.ui.custom_component.question_with_button;

import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import java.util.ArrayList;
import java.util.List;

public class RatioQuestionWithButtons extends QuestionWithButtons {

  // string string int för pluppar

  private String start, end;
  private int choices;
  private RadioButtonGroup<String> radioOptions;
  private List<String> options = new ArrayList<>();

  public RatioQuestionWithButtons(RatioQuestion question, CreateSurveyView survey) {
    super(question, survey);

    setStart(question.getStart());
    setEnd(question.getEnd());
    setChoices(question.getChoices());

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
