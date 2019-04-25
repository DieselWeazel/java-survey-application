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

  private Survey thisSurvey;
  private int typeOfQuestion;
  private int questionPosition;

  private SurveyRepository surveyRepository;

  public CreateSurveyView(SurveyRepository surveyRepository) {

    this.surveyRepository = surveyRepository;
    questionPosition = 1;
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
        System.out.println("Picked Text");
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
      question.setQuestionTitle(questionTitle.getValue());
      question.setPosition(questionPosition);
      System.out.println("Save Question" + questionPosition);
      questionPosition++;

      thisSurvey.getQuestionList().add(question);
    } else if (typeOfQuestion == 1 || typeOfQuestion == 2) {
      Question question = new MultiQuestion();
      question.setQuestionTitle(questionTitle.getValue());
      question.setPosition(questionPosition);
      // Add question alternative textfield value
      // question.getAlternativeList().add();
      questionPosition++;

      thisSurvey.getQuestionList().add(question);
    } else {
      /*
       * Felhantering ifall typ av fråga inte är rätt gjord (?)
       */
    }

  }

}
