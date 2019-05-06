package com.considlia.survey.custom_component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

@StyleSheet("css/app.css")
public class RadioQuestionWithButtons extends VerticalLayout {

  private static final int MOVE_UP = -1;
  private static final int MOVE_DOWN = 1;

  private String question;
  private Set<MultiQuestionAlternative> alternatives;
  private List<String> stringAlternatives;
  private int questionType;

  private HorizontalLayout content;

  public RadioQuestionWithButtons(String question, CreateSurveyView survey,
      List<String> stringAlternatives, int questionType) {
    setId("multicustom");
    setWidth("100%");

    this.questionType = questionType;
    this.question = question;
    content = new HorizontalLayout();
    content.setClassName("content");
    alternatives = new HashSet<>();
    this.stringAlternatives = stringAlternatives;

    for (int position = 0; position < stringAlternatives.size(); position++) {
      MultiQuestionAlternative alt = new MultiQuestionAlternative();
      alt.setPosition(position);
      alt.setAlternativeTitle(stringAlternatives.get(position));
      alternatives.add(alt);

    }

    content.setWidthFull();

    H5 title = new H5(question);
    title.setWidth("90%");
    RadioButtonGroup<String> radioButtons = new RadioButtonGroup<>();
    radioButtons.setItems(stringAlternatives);

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
    add(radioButtons);

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
