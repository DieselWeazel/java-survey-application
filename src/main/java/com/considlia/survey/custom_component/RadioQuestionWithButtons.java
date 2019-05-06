package com.considlia.survey.custom_component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;

@StyleSheet("css/app.css")
public class RadioQuestionWithButtons extends VerticalLayout {

  private static final int MOVE_UP = -1;
  private static final int MOVE_DOWN = 1;

  private String question;
  private Set<MultiQuestionAlternative> alternatives;
  private List<String> stringAlternatives;
  private int questionType;
  private H5 title;

  private RadioButtonGroup<String> radioButtons;
  private CheckboxGroup<String> checkBoxButtons;

  private HorizontalLayout content;

  public RadioQuestionWithButtons(String question, CreateSurveyView survey,
      List<String> stringAlternatives, int questionType) {
    setId("custom");
    setWidth("100%");

    this.questionType = questionType;
    this.question = question;
    this.stringAlternatives = stringAlternatives;
    content = new HorizontalLayout();
    content.setWidthFull();
    content.setClassName("content");
    alternatives = new HashSet<>();
    title = new H5(question);
    title.setWidth("90%");

    for (int position = 0; position < stringAlternatives.size(); position++) {
      MultiQuestionAlternative alt = new MultiQuestionAlternative();
      alt.setPosition(position);
      alt.setAlternativeTitle(stringAlternatives.get(position));
      alternatives.add(alt);
    }

    content.add(title);
    content.add(new Button(new Icon(VaadinIcon.ARROW_UP),
        event -> survey.moveQuestion(event.getSource(), MOVE_UP)));
    content.add(new Button(new Icon(VaadinIcon.ARROW_DOWN),
        event -> survey.moveQuestion(event.getSource(), MOVE_DOWN)));
    content.add(
        new Button(new Icon(VaadinIcon.PENCIL), onEdit -> survey.editQuestion(onEdit.getSource())));
    content.add(
        new Button(new Icon(VaadinIcon.TRASH), event -> survey.removeQuestion(event.getSource())));

    add(content);

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

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
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
  }

}
