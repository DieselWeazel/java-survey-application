package com.considlia.survey.ui.custom_component.question_with_button;

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

/**
 * Class extending {@link QuestionWithButtons}. Contains the specific {@link Component}s for the
 * subclasses of {@link MultiQuestion}
 *
 */
@StyleSheet("css/app.css")
public class MultiQuestionWithButtons extends QuestionWithButtons {

  private List<String> stringAlternatives;
  private MultiQuestion question;

  /**
   * Calls the super constructor. Also adds either a {@link RadioButtonGroup} or a
   * {@link CheckboxGroup} depending on the {@link QuestionType} from
   * {@link MultiQuestion#getQuestionType()}
   * 
   * @param question get {@link QuestionType} and also for passing it to super
   * @param survey passing it to super
   */
  public MultiQuestionWithButtons(MultiQuestion question, CreateSurveyView survey) {
    super(question, survey);

    this.stringAlternatives = question.getStringAlternatives();
    this.question = question;

    if (question.getQuestionType() == QuestionType.RADIO) {
      RadioButtonGroup<String> radioButtons = new RadioButtonGroup<>();
      radioButtons.setItems(stringAlternatives);
      radioButtons.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
      add(radioButtons);

    } else if (question.getQuestionType() == QuestionType.CHECKBOX) {
      CheckboxGroup<String> checkBoxButtons = new CheckboxGroup<>();
      checkBoxButtons.setItems(stringAlternatives);
      checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
      add(checkBoxButtons);
    }
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
