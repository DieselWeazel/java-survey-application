package com.considLia.survey.ui;

import java.time.LocalDate;
import com.considLia.survey.custom_component.RadioQuestionWithButtons;
import com.considLia.survey.custom_component.TextQuestionWithButtons;
import com.considLia.survey.model.MultiQuestion;
import com.considLia.survey.model.Survey;
import com.considLia.survey.model.TextQuestion;
import com.considLia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
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
  private RadioButtonGroup<String> radioButtons;

  private TextField surveyTitleTextField;
  private TextField creatorNameTextField;
  private TextField questionTitleTextField;

  private HorizontalLayout header;
  private VerticalLayout questions;
  private VerticalLayout addQuestionPackage;

  private Survey thisSurvey;
  private int typeOfQuestion;
  private static final int TEXT_QUESTION = 0;
  private static final int RADIO_QUESTION = 1;
  private static final int BOX_QUESTION = 2;

  private SurveyRepository surveyRepository;

  public CreateSurveyView(SurveyRepository surveyRepository) {
    setId("createsurvey");

    this.surveyRepository = surveyRepository;
    thisSurvey = new Survey();

    header = new HorizontalLayout();
    questions = new VerticalLayout();
    addQuestionPackage = new VerticalLayout();

    addQuestionButton = new Button("Add question", event -> addQuestion());
    submitSurveyButton = new Button("submit", event -> saveSurvey());
    submitSurveyButton.setEnabled(false);

    surveyTitleTextField = new TextField();
    creatorNameTextField = new TextField();
    questionTitleTextField = new TextField();
    questionTitleTextField.setValueChangeMode(ValueChangeMode.EAGER);

    surveyTitleTextField.setLabel("Survey title");
    surveyTitleTextField.setPlaceholder("Survey title");
    surveyTitleTextField.setWidth("400px");
    surveyTitleTextField.setValueChangeMode(ValueChangeMode.EAGER);
    creatorNameTextField.setLabel("Created by");
    creatorNameTextField.setPlaceholder("Created by");
    creatorNameTextField.setWidth("250px");
    creatorNameTextField.setValueChangeMode(ValueChangeMode.EAGER);

    header.setClassName("createheader");
    addQuestionPackage.setClassName("questionpackage");
    header.add(surveyTitleTextField, creatorNameTextField);

    surveyTitleTextField.addValueChangeListener(titleChange -> checkFilledFields());
    creatorNameTextField.addValueChangeListener(titleChange -> checkFilledFields());

    add(header);
    add(questions);
    add(addQuestionPackage);
    add(addQuestionButton);
    add(submitSurveyButton);

  }

  // Create addQuestion-package with listeners, if already created: save the question
  public void addQuestion() {

    if (radioButtons != null) {
      if (typeOfQuestion == TEXT_QUESTION) {
        questions.add(new TextQuestionWithButtons(questionTitleTextField.getValue(), this));
      } else {
        // questions.add(new RadioQuestionWithButtons(questionTitleTextField.getValue(), this));
      }
      questionTitleTextField.setValue("");
      radioButtons.setValue("");
      checkFilledFields();
    } else {
      radioButtons = new RadioButtonGroup<>();
      radioButtons.setItems("Text question", "Radio Question", "Checkbox Question");
      questionTitleTextField.setWidth("300px");
      questionTitleTextField.setPlaceholder("Question title");
      questionTitleTextField.setLabel("Question title");
      addQuestionPackage.add(questionTitleTextField);
      addQuestionPackage.add(radioButtons);

      radioButtons.addValueChangeListener(event -> {
        if (event.getValue().equalsIgnoreCase("Text question")
            && !questionTitleTextField.getValue().isEmpty()) {
          addQuestionButton.setEnabled(true);
          if (event.getValue().equalsIgnoreCase("Text question")) {
            typeOfQuestion = TEXT_QUESTION;
          } else if (event.getValue().equalsIgnoreCase("Multi question")) {
            typeOfQuestion = RADIO_QUESTION;
          } else if (event.getValue().equalsIgnoreCase("Checkbox Question")) {
            typeOfQuestion = BOX_QUESTION;
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
            typeOfQuestion = TEXT_QUESTION;
          } else if (radioButtons.getValue().equalsIgnoreCase("Multi question")) {
            typeOfQuestion = RADIO_QUESTION;
          } else if (radioButtons.getValue().equalsIgnoreCase("Checkbox Question")) {
            typeOfQuestion = BOX_QUESTION;
          }
        }
      });
    }
    addQuestionButton.setEnabled(false);
  }

  // Move question in questionscontainer (add error handling), NOT COMPLETE for other than
  // textquestion
  public void moveQuestion(Button button, int moveDirection) {
    HorizontalLayout component = (HorizontalLayout) button.getParent().get();
    questions.replace(component,
        questions.getComponentAt(questions.indexOf(component) + moveDirection));
  }

  // Remove questions from questionscontainer, NOT COMPLETE for other than textquestion
  public void removeQuestion(Button button) {
    questions.remove(button.getParent().get());
    checkFilledFields();
  }

  // Save survey with questions to database(multiquestion not implemented)
  public void saveSurvey() {
    for (int position = 0; position < questions.getComponentCount(); position++) {

      if (questions.getComponentAt(position) instanceof TextQuestionWithButtons) {
        TextQuestionWithButtons component =
            (TextQuestionWithButtons) questions.getComponentAt(position);
        TextQuestion question = new TextQuestion();
        question.setQuestionTitle(component.getQuestion());
        question.setPosition(position);
        thisSurvey.getQuestionList().add(question);

      } else if (questions.getComponentAt(position) instanceof RadioQuestionWithButtons) {
        RadioQuestionWithButtons component =
            (RadioQuestionWithButtons) questions.getComponentAt(position);
        MultiQuestion question = new MultiQuestion();
        question.setQuestionTitle(component.getQuestion());
        question.setPosition(position);
        question.setQuestionType(component.getQuestionType());
        question.getAlternativeList().addAll(component.getAlternatives());

        thisSurvey.getQuestionList().add(question);

      }

    }
    thisSurvey.setCreator(creatorNameTextField.getValue());
    thisSurvey.setSurveyTitle(surveyTitleTextField.getValue());
    thisSurvey.setDate(LocalDate.now());

    surveyRepository.save(thisSurvey);

    getUI().ifPresent(ui -> ui.navigate(""));
  }

  // Make custom question component and add it to questionscontainer
  public void saveQuestion(String questionTitle, int typeOfQuestion) {

    if (typeOfQuestion == TEXT_QUESTION) {

    } else if (typeOfQuestion == RADIO_QUESTION) {

    } else if (typeOfQuestion == BOX_QUESTION) {

    } else {

      /*
       * Felhantering ifall typ av fråga inte är rätt gjord (?)
       */

    }

  }

  // Control if questions list is not 0
  public boolean validateQuestionListLength() {
    return questions.getComponentCount() != 0;
  }

  // Check if title and creator textfields are filled out
  public void checkFilledFields() {
    if (!(surveyTitleTextField.isEmpty() || creatorNameTextField.isEmpty())
        && validateQuestionListLength()) {
      submitSurveyButton.setEnabled(true);
    } else {
      submitSurveyButton.setEnabled(false);
    }
  }
}
