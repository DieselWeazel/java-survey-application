package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.answer.Answers;
import com.considlia.survey.model.answer.ChoiceAnswer;
import com.considlia.survey.model.answer.TextAnswer;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.MultiQuestionAlternative;
import com.considlia.survey.model.question.Question;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.function.Consumer;

public class ReadMultiQuestionLayout extends ReadQuestionLayout implements ReadQuestionComponent {

  private RadioButtonGroup<MultiQuestionAlternative> radioButtons;
  private CheckboxGroup<MultiQuestionAlternative> checkBoxButtons;
  private TextAnswer textAnswer;
  private Binder<ChoiceAnswer> binder;

  public ReadMultiQuestionLayout(MultiQuestion question) {
    super(question);
    if (question.getQuestionType() == QuestionType.RADIO) {
      this.radioButtons = new RadioButtonGroup<>();
      radioButtons.setItems(question.getAlternatives());
      add(radioButtons);
      radioButtons.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

    } else {
      this.checkBoxButtons = new CheckboxGroup<>();
      checkBoxButtons.setItems(question.getAlternatives());
      add(checkBoxButtons);
      checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    }
  }

  public RadioButtonGroup<MultiQuestionAlternative> getRadioButtons() {
    return radioButtons;
  }

  public CheckboxGroup<MultiQuestionAlternative> getCheckBoxButtons() {
    return checkBoxButtons;
  }

  @Override
  public Answers gatherResponse() throws ValidationException {
    return null;
  }
}
