package com.considlia.survey.ui;

import java.time.LocalDate;
import com.considlia.survey.custom_component.RadioQuestionWithButtons;
import com.considlia.survey.custom_component.TextQuestionWithButtons;
import com.considlia.survey.model.MultiQuestion;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.considlia.survey.model.Question;
import com.considlia.survey.model.Survey;
import com.considlia.survey.model.TextQuestion;
import com.considlia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

@StyleSheet("css/app.css")
@Route(value = "createsurvey", layout = MainLayout.class)
public class CreateSurveyView extends VerticalLayout implements HasUrlParameter<Long> {

  // Buttons
  private Button addQuestionButton;
  private Button submitSurveyButton;
  private RadioButtonGroup<String> radioButtons;

  // Textfields
  private TextField surveyTitleTextField;
  private TextField creatorNameTextField;
  private TextField questionTitleTextField;

  // Containers
  private HorizontalLayout header;
  private VerticalLayout questions;
  private VerticalLayout addQuestionPackage;

  // Private variables used when creating the survey
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
  // Add handling for multiquestions
  public void addQuestion() {

    questionTitleTextField.focus();

    if (radioButtons != null) {
      if (typeOfQuestion == TEXT_QUESTION) {
        questions.add(new TextQuestionWithButtons(questionTitleTextField.getValue(), this));
      } else {
        // questions.add(new RadioQuestionWithButtons(questionTitleTextField.getValue(), this));
      }
      questionTitleTextField.setValue("");
      radioButtons.setValue("");
      checkFilledFields();
    }
    // Only enters here on the first time pressing the add question button
    // Because radiobuttons is null only at that time
    else {
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

      // Checks if the questionTitle and questionType is set
      questionTitleTextField.addValueChangeListener(event -> {
        if (questionTitleTextField.isEmpty() || (radioButtons.getValue() == null)
            || (radioButtons.getValue().isEmpty())) {
          addQuestionButton.setEnabled(false);
        } else {
          addQuestionButton.setEnabled(true);
        }
      });
    }
    addQuestionButton.setEnabled(false);
  }

  // Move question in questionscontainer, NOT COMPLETE for other than textquestion
  public void moveQuestion(Button button, int moveDirection) {
    HorizontalLayout component = (HorizontalLayout) button.getParent().get();
    if (questions.indexOf(component) == 0 && moveDirection == -1
        || questions.indexOf(component) == questions.getComponentCount() - 1
            && moveDirection == 1) {
      return;
    }
    questions.replace(component,
        questions.getComponentAt(questions.indexOf(component) + moveDirection));
  }

  // Remove questions from questionscontainer, NOT COMPLETE for other than textquestion
  public void removeQuestion(Button button) {
    questions.remove(button.getParent().get());
    checkFilledFields();
  }

  // Edit question via pencil buttons in custom components
  public void editQuestion(Button button) {
    Dialog dialog = new Dialog();
    Button confirm = new Button("Confirm");
    TextField newTitleTextField = new TextField();

    dialog.open();
    dialog.add(newTitleTextField);
    if (button.getParent().get() instanceof TextQuestionWithButtons) {
      TextQuestionWithButtons choosenQuestion = (TextQuestionWithButtons) button.getParent().get();

      newTitleTextField.setValue(choosenQuestion.getQuestion());

      confirm.addClickListener(event -> {
        choosenQuestion.setQuestion(newTitleTextField.getValue());
        dialog.close();
      });

    } else {
      RadioQuestionWithButtons choosenQuestion =
          (RadioQuestionWithButtons) button.getParent().get();

      newTitleTextField.setValue(choosenQuestion.getQuestion());

      for (MultiQuestionAlternative s : choosenQuestion.getAlternatives()) {
        TextField alternative = new TextField();
        alternative.setValue(s.getAlternativeTitle());
        dialog.add(alternative);
      }
    }

    dialog.add(new HorizontalLayout(new Button("Cancel", onCancel -> dialog.close()), confirm));
  }

  // Save survey with questions to database(multiquestion not implemented)
  public void saveSurvey() {
    thisSurvey.getQuestionList().clear();

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

  // HasUrlParameter function, if parameter is null, do nothing but load the view as normal.
  // If parameter has an surveyId, load questions, title and creator. Add Multiquestion
  @Override
  public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
    if (parameter == null) {
      // Do nothing
    } else {
      thisSurvey = surveyRepository.getSurveyBySurveyId(parameter);

      for (Question q : thisSurvey.getQuestionList()) {
        surveyTitleTextField.setValue(thisSurvey.getSurveyTitle());
        creatorNameTextField.setValue(thisSurvey.getCreator());
        if (q instanceof TextQuestion) {
          questions.add(new TextQuestionWithButtons(q.getQuestionTitle(), this));
        } else {
          // questions.add(new RadioQuestionWithButtons(questionTitleTextField.getValue(), this));
        }
      }
      thisSurvey.getQuestionList().clear();
      checkFilledFields();
    }

  }
}
