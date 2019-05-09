package com.considlia.survey.custom_component.question_with_button;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;

@StyleSheet("css/app.css")
public class RadioQuestionWithButtons extends QuestionWithButtons {

  private Set<MultiQuestionAlternative> alternatives;
  private List<String> stringAlternatives;
  private int questionType;

  private RadioButtonGroup<String> radioButtons;
  private CheckboxGroup<String> checkBoxButtons;

  public RadioQuestionWithButtons(String question, CreateSurveyView survey,
      List<String> stringAlternatives, int questionType) {
    super(question, survey);

    this.questionType = questionType;
    this.stringAlternatives = stringAlternatives;

    alternatives = new HashSet<>();

    updateAlternatives();

    if (questionType == 1) {
      radioButtons = new RadioButtonGroup<>();
      radioButtons.setItems(stringAlternatives);
      radioButtons.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
      add(radioButtons);

    } else {
      checkBoxButtons = new CheckboxGroup<>();
      checkBoxButtons.setItems(stringAlternatives);
      checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
      add(checkBoxButtons);
    }

  }

  public void updateAlternatives() {
    alternatives.clear();
    for (int position = 0; position < stringAlternatives.size(); position++) {
      MultiQuestionAlternative alt = new MultiQuestionAlternative();
      alt.setPosition(position);
      alt.setAlternativeTitle(stringAlternatives.get(position));
      alternatives.add(alt);
    }
  }

  public Set<MultiQuestionAlternative> getAlternatives() {
    return alternatives;
  }

  public void setAlternatives(Set<MultiQuestionAlternative> alternatives) {
    this.alternatives = alternatives;
  }

  public int getQuestionType() {
    return questionType;
  }

  public void setQuestionType(int questionType) {
    this.questionType = questionType;

  }

  public List<String> getStringAlternatives() {
    return stringAlternatives;
  }

  public void setStringAlternatives(List<String> stringAlternatives) {
    this.stringAlternatives = stringAlternatives;

    updateAlternatives();

    if (questionType == 1) {
      radioButtons.setItems(stringAlternatives);
    } else {
      checkBoxButtons.setItems(stringAlternatives);
    }

  }

}
