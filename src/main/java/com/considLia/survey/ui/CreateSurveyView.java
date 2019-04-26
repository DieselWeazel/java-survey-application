package com.considLia.survey.ui;

import java.time.LocalDate;
import com.considLia.survey.model.MultiQuestion;
import com.considLia.survey.model.Question;
import com.considLia.survey.model.Survey;
import com.considLia.survey.model.TextQuestion;
import com.considLia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@StyleSheet("css/app.css")
@Route(value = "createsurvey", layout = MainLayout.class)
public class CreateSurveyView extends VerticalLayout {

  private Button addQuestionButton;
  private Button submitSurveyButton;
  RadioButtonGroup<String> radioButtons;

  private TextField surveyTitleTextField;
  private TextField creatorNameTextField;
  private TextField questionTitleTextField;

  private HorizontalLayout horizontalTextfieldContainer;

  private Survey thisSurvey;
  private int typeOfQuestion;
  private int questionPosition;
  private boolean addAndSave;

  private SurveyRepository surveyRepository;

  public CreateSurveyView(SurveyRepository surveyRepository) {
    setId("createsurvey");

    this.surveyRepository = surveyRepository;
    this.questionPosition = 1;
    this.addAndSave = false;
    this.thisSurvey = new Survey();
    this.horizontalTextfieldContainer = new HorizontalLayout();
    this.addQuestionButton = new Button("Add question", event -> addQuestion());
    this.submitSurveyButton = new Button("submit", event -> saveSurvey());
    this.surveyTitleTextField = new TextField();
    this.creatorNameTextField = new TextField();
    this.questionTitleTextField = new TextField();
    questionTitleTextField.setValueChangeMode(ValueChangeMode.EAGER);

    surveyTitleTextField.setLabel("Survey title");
    surveyTitleTextField.setPlaceholder("Survey title");
    surveyTitleTextField.setWidth("400px");
    creatorNameTextField.setLabel("Created by");
    creatorNameTextField.setPlaceholder("Created by");
    creatorNameTextField.setWidth("250px");
    horizontalTextfieldContainer.setClassName("staticcontainer");

    horizontalTextfieldContainer.add(surveyTitleTextField, creatorNameTextField);
    add(horizontalTextfieldContainer);
    add(addQuestionButton);
    add(submitSurveyButton);

  }

  public void addQuestion() {

    if (radioButtons != null) {
      remove(radioButtons);
      saveQuestion(typeOfQuestion);
    }

    questionTitleTextField.setPlaceholder("Question title");
    typeOfQuestion = -1;

    addQuestionButton.setEnabled(false);

    radioButtons = new RadioButtonGroup<>();
    radioButtons.setItems("Text question", "Radio Question", "Checkbox Question");

    radioButtons.addValueChangeListener(event -> {
      if (event.getValue().equalsIgnoreCase("Text question")
          && !questionTitleTextField.getValue().isEmpty()) {
        addQuestionButton.setEnabled(true);
        if (event.getValue().equalsIgnoreCase("Text question")) {
          typeOfQuestion = 0;
        } else if (event.getValue().equalsIgnoreCase("Multi question")) {
          typeOfQuestion = 1;
        } else if (event.getValue().equalsIgnoreCase("Checkbox Question")) {
          typeOfQuestion = 2;
        }
      }
      if (questionTitleTextField.isEmpty()) {
        addQuestionButton.setEnabled(false);
      }
    });

    questionTitleTextField.addValueChangeListener(event -> {
      if (questionTitleTextField.isEmpty()) {
        addQuestionButton.setEnabled(false);
      }
      if (radioButtons.getValue() != null) {
        if (radioButtons.getValue().equalsIgnoreCase("Text question")
            && !questionTitleTextField.getValue().isEmpty()) {
          addQuestionButton.setEnabled(true);
          typeOfQuestion = 0;
        } else if (radioButtons.getValue().equalsIgnoreCase("Multi question")) {
          typeOfQuestion = 1;
        } else if (radioButtons.getValue().equalsIgnoreCase("Checkbox Question")) {
          typeOfQuestion = 2;
        }
      }

    });

    remove(addQuestionButton);
    remove(submitSurveyButton);
    add(questionTitleTextField);
    add(radioButtons);
    add(addQuestionButton);
    add(submitSurveyButton);
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

      add(new H3(question.getQuestionTitle()));

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
