package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.question.RatioQuestion;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import java.util.ArrayList;
import java.util.List;

public class ReadRatioQuestionLayout extends ReadQuestionLayout {

  private RadioButtonGroup<String> radioOptions;
  private List<String> options = new ArrayList<>();

  public ReadRatioQuestionLayout(RatioQuestion question) {
    super(question);
    for (int i = 1; i <= question.getChoices(); i++) {
      if (i == 1) {
        options.add(Integer.toString(i) + " " + question.getStart());
      } else if (i == question.getChoices()) {
        options.add(Integer.toString(i) + " " + question.getEnd());
      } else {
        options.add(Integer.toString(i));
      }
    }

    radioOptions = new RadioButtonGroup<>();
    radioOptions.setItems(options);
    radioOptions.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
    add(radioOptions);
  }

  public RadioButtonGroup<String> getRadioButtons() {
    return radioOptions;
  }
}
