package com.considlia.survey.custom_component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

public class RadioQuestionWithButtons extends VerticalLayout {

  private static final int MOVE_UP = -1;
  private static final int MOVE_DOWN = 1;

  private String question;
  private Set<MultiQuestionAlternative> alternatives;
  private int questionType;

  private HorizontalLayout content;

  public RadioQuestionWithButtons(String question, CreateSurveyView survey,
      List<String> stringAlternatives, int questionType) {

    alternatives = new HashSet<>();
    this.questionType = questionType;
    this.question = question;
    content = new HorizontalLayout();

    for (int position = 0; position < stringAlternatives.size(); position++) {
      MultiQuestionAlternative alt = new MultiQuestionAlternative();
      alt.setPosition(position);
      alt.setAlternativeTitle(stringAlternatives.get(position));
      alternatives.add(alt);
    }

    content.setWidth("100%");
    H5 title = new H5(question);
    title.setWidth("90%");
    RadioButtonGroup<String> radioButtons = new RadioButtonGroup<>();
    radioButtons.setItems(stringAlternatives);

    content.add(title);
    content.add(new Button(new Icon(VaadinIcon.ARROW_UP),
        event -> survey.moveQuestion(event.getSource(), MOVE_UP)));
    content.add(new Button(new Icon(VaadinIcon.ARROW_DOWN),
        event -> survey.moveQuestion(event.getSource(), MOVE_DOWN)));
    content.add(new Button(new Icon(VaadinIcon.PENCIL)));
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

}
