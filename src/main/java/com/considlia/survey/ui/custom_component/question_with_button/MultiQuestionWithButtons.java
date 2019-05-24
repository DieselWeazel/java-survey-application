package com.considlia.survey.ui.custom_component.question_with_button;

import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import java.util.List;

@StyleSheet("css/app.css")
public class MultiQuestionWithButtons extends QuestionWithButtons {

  private List<String> stringAlternatives;
  private MultiQuestion question;

  private RadioButtonGroup<String> radioButtons;
  private CheckboxGroup<String> checkBoxButtons;

  public MultiQuestionWithButtons(MultiQuestion question, CreateSurveyView survey) {
    super(question, survey);

    this.stringAlternatives = question.getStringAlternatives();
    this.question = question;

    // updateAlternatives();

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
  //
  // public void updateAlternatives() {
  // getQuestion().getAlternatives().clear();
  // for (int position = 0; position < stringAlternatives.size(); position++) {
  // MultiQuestionAlternative alt = new MultiQuestionAlternative();
  // alt.setPosition(position);
  // alt.setTitle(stringAlternatives.get(position));
  // getQuestion().getAlternatives().add(alt);
  // }
  // }

  @Override
  public MultiQuestion getQuestion() {
    return question;
  }

  public List<String> getStringAlternatives() {
    return stringAlternatives;
  }

  // public void setStringAlternatives(List<String> stringAlternatives) {
  // this.stringAlternatives = stringAlternatives;
  //
  // updateAlternatives();
  //
  // if (getQuestion().getQuestionType() == QuestionType.RADIO) {
  // radioButtons.setItems(stringAlternatives);
  // } else {
  // checkBoxButtons.setItems(stringAlternatives);
  // }
  //
  // }

}
