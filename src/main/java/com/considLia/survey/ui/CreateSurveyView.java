package com.considLia.survey.ui;

import java.time.LocalDate;
import com.considLia.survey.custom_component.TextQuestionWithButtons;
import com.considLia.survey.model.MultiQuestion;
import com.considLia.survey.model.Question;
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
  private int questionPosition;
  private static final int TEXT_QUESTION = 0;
  private static final int RADIO_QUESTION = 1;
  private static final int BOX_QUESTION = 2;

  private SurveyRepository surveyRepository;

  public CreateSurveyView(SurveyRepository surveyRepository) {
    setId("createsurvey");

    this.surveyRepository = surveyRepository;
    questionPosition = 1;
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
      saveQuestion(questionTitleTextField.getValue(), typeOfQuestion);
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

  // Move question in questionscontainer (add error handling)
  public void moveQuestion(Button button, int moveDirection) {
    HorizontalLayout component = (HorizontalLayout) button.getParent().get();
    questions.replace(component,
        questions.getComponentAt(questions.indexOf(component) + moveDirection));
  }

  // Remove questions from questionscontainer
  public void removeQuestion(Button button) {
    HorizontalLayout component = (HorizontalLayout) button.getParent().get();
    questions.remove(component);
    checkFilledFields();

  }

  // Save survey with questions to database(multiquestion not implemented)
  public void saveSurvey() {
    for (int position = 0; position < questions.getComponentCount(); position++) {
      Question question;

      if (questions.getComponentAt(position) instanceof TextQuestionWithButtons) {
        System.out.println("spara textfråga");
        TextQuestionWithButtons component =
            (TextQuestionWithButtons) questions.getComponentAt(position);
        question = new TextQuestion();
        question.setQuestionTitle(component.getQuestion());
        question.setPosition(position);
        thisSurvey.getQuestionList().add(question);
      } else {
        System.out.println("Spara multifråga");
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

      questions.add(new TextQuestionWithButtons(questionTitle, this));

    } else if (typeOfQuestion == RADIO_QUESTION || typeOfQuestion == BOX_QUESTION) {
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
    questionTitleTextField.setValue("");
    radioButtons.setValue("");
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
