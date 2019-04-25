package com.considLia.survey.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "createsurvey", layout = MainLayout.class)
public class CreateSurveyView extends VerticalLayout {

  Button addQuestion = new Button("Add question", event -> addQuestion());

  public CreateSurveyView() {
    HorizontalLayout horizontalContainer = new HorizontalLayout();

    TextField surveyTitle = new TextField();
    surveyTitle.setPlaceholder("Survey title");

    TextField creatorName = new TextField();
    creatorName.setPlaceholder("Created by");

    horizontalContainer.add(surveyTitle, creatorName);

    add(horizontalContainer);
    add(addQuestion);
  }

  public void addQuestion() {

  }


}
