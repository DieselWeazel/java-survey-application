package com.considlia.survey.ui.custom_component.question_with_button;

import com.considlia.survey.model.question.Question;
import java.util.List;
import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import java.util.function.Consumer;

/**
 * Class extending {@link QuestionWithButtons}. Contains the specific {@link Component}s for the
 * subclasses of {@link MultiQuestion}
 *
 */
@StyleSheet("css/app.css")
public class MultiQuestionWithButtons extends QuestionWithButtons {

  private List<String> stringAlternatives;
  private MultiQuestion question;

  private RadioButtonGroup<String> radioButtons;
  private CheckboxGroup<String> checkBoxButtons;

  /**
   * Calls the super constructor. Also adds either a {@link RadioButtonGroup} or a
   * {@link CheckboxGroup} depending on the {@link QuestionType} from
   * {@link MultiQuestion#getQuestionType()}
   * 
   * @param question getList {@link QuestionType} and also for passing it to super
   * @param survey passing it to super
   */
  public MultiQuestionWithButtons(MultiQuestion question, CreateSurveyView survey, Consumer<Question> deleteQuestionConsumer) {
    super(question, survey, deleteQuestionConsumer);

    this.stringAlternatives = question.getStringAlternatives();
    this.question = question;

    if (question.getQuestionType() == QuestionType.RADIO) {
      radioButtons = new RadioButtonGroup<>();
      radioButtons.setItems(stringAlternatives);
      radioButtons.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
      add(radioButtons);

    } else if (question.getQuestionType() == QuestionType.CHECKBOX) {
      checkBoxButtons = new CheckboxGroup<>();
      checkBoxButtons.setItems(stringAlternatives);
      checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
      add(checkBoxButtons);
    }
  }

  /**
   * Updates the GUI part associated with the {@link MultiQuestion}.
   * 
   * @param stringAlternatives
   */
  public void updateGUI(List<String> stringAlternatives) {
    updateStringAlternatives(stringAlternatives);

    if (question.getQuestionType() == QuestionType.RADIO) {
      RadioButtonGroup<String> newRadioButtons = new RadioButtonGroup<>();
      newRadioButtons.setItems(question.getStringAlternatives());
      newRadioButtons.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

      replace(radioButtons, newRadioButtons);
      radioButtons = newRadioButtons;

    } else if (question.getQuestionType() == QuestionType.CHECKBOX) {
      CheckboxGroup<String> newCheckBox = new CheckboxGroup<>();
      newCheckBox.setItems(question.getStringAlternatives());
      newCheckBox.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

      replace(checkBoxButtons, newCheckBox);
      checkBoxButtons = newCheckBox;
    }
  }

  /**
   * Uses the {@link MultiQuestion#updateAlternatives(List)} to update the alternatives.
   *
   * @param stringAlternatives - the list of the alternatives
   */
  public void updateStringAlternatives(List<String> stringAlternatives) {
    getQuestion().updateAlternatives(stringAlternatives);
  }

  /**
   * Returns {@link MultiQuestion}
   * 
   * @return {@link MultiQuestion}
   */
  @Override
  public MultiQuestion getQuestion() {
    return question;
  }

  /**
   * Returns a list of Strings
   * 
   * @return list of Strings
   */
  public List<String> getStringAlternatives() {
    return stringAlternatives;
  }

}
