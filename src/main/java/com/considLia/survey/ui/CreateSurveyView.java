package com.considLia.survey.ui;

import com.considLia.survey.model.Survey;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "createsurvey", layout = MainLayout.class)
public class CreateSurveyView extends VerticalLayout {

  Button addQuestion = new Button("Add question", event -> addQuestion());
  TextField surveyTitle = new TextField();
  TextField creatorName = new TextField();
  TextField questionTitle = new TextField();



  public CreateSurveyView() {
    HorizontalLayout horizontalContainer = new HorizontalLayout();

    surveyTitle.setPlaceholder("Survey title");

    creatorName.setPlaceholder("Created by");

    horizontalContainer.add(surveyTitle, creatorName);

    add(horizontalContainer);
    add(addQuestion);
  }

  public void addQuestion() {
    questionTitle.setPlaceholder("Question title");
    Button save = new Button("submit", event -> saveSurvey());

    RadioButtonGroup<String> radioButtons = new RadioButtonGroup<>();
    radioButtons.setItems("Text question", "Radio Question", "Checkbox Question");
    radioButtons.addValueChangeListener(event -> {
      if (event.getValue().equalsIgnoreCase("Text question")) {

      } else if (event.getValue().equalsIgnoreCase("Multi question")) {

      } else if (event.getValue().equalsIgnoreCase("Checkbox Question")) {

      }
    });

    add(questionTitle);
    add(radioButtons);
    add(save);
  }

  public void saveSurvey() {
    Survey newSurvey = new Survey();

    newSurvey.setCreator(creatorName.getValue());
  }

}
