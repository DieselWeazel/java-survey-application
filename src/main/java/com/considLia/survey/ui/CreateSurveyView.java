package com.considLia.survey.ui;

import java.time.LocalDate;
import com.considLia.survey.model.MultiQuestion;
import com.considLia.survey.model.Question;
import com.considLia.survey.model.Survey;
import com.considLia.survey.model.TextQuestion;
import com.considLia.survey.repositories.SurveyRepository;
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

  Survey thisSurvey;
  private int typeOfQuestion;

  private SurveyRepository surveyRepository;

  public CreateSurveyView(SurveyRepository surveyRepository) {

    this.surveyRepository = surveyRepository;
    thisSurvey = new Survey();

    HorizontalLayout horizontalContainer = new HorizontalLayout();

    surveyTitle.setPlaceholder("Survey title");

    creatorName.setPlaceholder("Created by");

    horizontalContainer.add(surveyTitle, creatorName);

    add(horizontalContainer);
    add(addQuestion);
  }

  public void addQuestion() {
    questionTitle.setPlaceholder("Question title");

    typeOfQuestion = -1;

    Button save = new Button("submit", event -> saveQuestion(typeOfQuestion));

    RadioButtonGroup<String> radioButtons = new RadioButtonGroup<>();
    radioButtons.setItems("Text question", "Radio Question", "Checkbox Question");
    radioButtons.addValueChangeListener(event -> {
      if (event.getValue().equalsIgnoreCase("Text question")) {
        typeOfQuestion = 0;
      } else if (event.getValue().equalsIgnoreCase("Multi question")) {
        typeOfQuestion = 1;
      } else if (event.getValue().equalsIgnoreCase("Checkbox Question")) {
        typeOfQuestion = 2;
      }
    });

    add(questionTitle);
    add(radioButtons);
    add(save);
  }

  public void saveSurvey() {

    thisSurvey.setCreator(creatorName.getValue());
    thisSurvey.setSurveyTitle(surveyTitle.getValue());
    thisSurvey.setDate(LocalDate.now());

    surveyRepository.save(thisSurvey);

    thisSurvey = null;
  }

  public void saveQuestion(int typeOfQuestion) {

    if (typeOfQuestion == 0) {
      Question question = new TextQuestion();

      thisSurvey.getQuestionList().add(question);
    } else if (typeOfQuestion == 1 || typeOfQuestion == 2) {
      Question question = new MultiQuestion();

      thisSurvey.getQuestionList().add(question);
    }
  }

}
