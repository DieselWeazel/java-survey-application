package com.considLia.survey.custom_component;

import java.util.List;
import com.considLia.survey.ui.CreateSurveyView;
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
  private List<String> alternatives;

  private HorizontalLayout content;

  public RadioQuestionWithButtons(String question, CreateSurveyView survey,
      List<String> alternatives) {

    this.alternatives = alternatives;
    this.question = question;
    content = new HorizontalLayout();

    content.setWidth("100%");
    H5 title = new H5(question);
    title.setWidth("90%");
    RadioButtonGroup<String> radioButtons = new RadioButtonGroup<>();
    radioButtons.setItems(alternatives);

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

  public List<String> getAlternatives() {
    return alternatives;
  }

  public void setAlternatives(List<String> alternatives) {
    this.alternatives = alternatives;
  }

}
