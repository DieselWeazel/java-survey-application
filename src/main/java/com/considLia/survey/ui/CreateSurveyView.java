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

  private Button addQuestionButton;
  private TextField surveyTitleTextField;
  private TextField creatorNameTextField;
  private TextField questionTitleTextField;

  private HorizontalLayout horizontalTextfieldContainer;

  private Survey thisSurvey;
  private int typeOfQuestion;
  private int questionPosition;

  private SurveyRepository surveyRepository;

  public CreateSurveyView(SurveyRepository surveyRepository) {

    this.surveyRepository = surveyRepository;
    this.questionPosition = 1;
    this.thisSurvey = new Survey();
    this.horizontalTextfieldContainer = new HorizontalLayout();
    this.addQuestionButton = new Button("Add question", event -> addQuestion());
    this.surveyTitleTextField = new TextField();
    this.creatorNameTextField = new TextField();
    this.questionTitleTextField = new TextField();

    surveyTitleTextField.setPlaceholder("Survey title");
    creatorNameTextField.setPlaceholder("Created by");

    horizontalTextfieldContainer.add(surveyTitleTextField, creatorNameTextField);
    add(horizontalTextfieldContainer);
    add(addQuestionButton);
  }

  public void addQuestion() {
    questionTitleTextField.setPlaceholder("Question title");

    typeOfQuestion = -1;

    Button saveButton = new Button("submit", event -> saveQuestion(typeOfQuestion));

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

    add(questionTitleTextField);
    add(radioButtons);
    add(saveButton);
  }

  public void saveSurvey() {

    thisSurvey.setCreator(creatorNameTextField.getValue());
    thisSurvey.setSurveyTitle(surveyTitleTextField.getValue());
    thisSurvey.setDate(LocalDate.now());

    surveyRepository.save(thisSurvey);

    thisSurvey = null;
  }

  public void saveQuestion(int typeOfQuestion) {

    if (typeOfQuestion == 0) {
      Question question = new TextQuestion();
      question.setQuestionTitle(questionTitleTextField.getValue());
      question.setPosition(questionPosition);
      questionPosition++;

      thisSurvey.getQuestionList().add(question);
    } else if (typeOfQuestion == 1 || typeOfQuestion == 2) {
      Question question = new MultiQuestion();
      question.setQuestionTitle(questionTitleTextField.getValue());
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
