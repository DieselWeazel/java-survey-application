package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.answer.Answers;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RatioQuestion;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReadRatioQuestionLayout extends ReadQuestionLayout implements ReadQuestionComponent {

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

  @Override
  public Answers gatherResponse() throws ValidationException {
    return null;
  }
}
