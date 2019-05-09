package com.considlia.survey.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.considlia.survey.custom_component.ConfirmDialog;
import com.considlia.survey.custom_component.CreateAlternative;
import com.considlia.survey.custom_component.EditDialog;
import com.considlia.survey.custom_component.RadioQuestionWithButtons;
import com.considlia.survey.custom_component.question_with_button.TextQuestionWithButtons;
import com.considlia.survey.model.MultiQuestion;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.considlia.survey.model.Question;
import com.considlia.survey.model.Survey;
import com.considlia.survey.model.TextQuestion;
import com.considlia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveEvent.ContinueNavigationAction;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

@StyleSheet("css/app.css")
@Route(value = "createsurvey", layout = MainLayout.class)
public class CreateSurveyView extends VerticalLayout
    implements HasUrlParameter<Long>, BeforeLeaveObserver {

  // Buttons
  private Button addQuestionButton;
  private Button submitSurveyButton;
  private Button cancelButton;
  private RadioButtonGroup<String> radioButtons;

  // Textfields
  private TextField surveyTitleTextField;
  private TextField creatorNameTextField;
  private TextField questionTitleTextField;

  // Containers
  private HorizontalLayout header;
  private VerticalLayout questions;
  private VerticalLayout addQuestionPackage;
  private HorizontalLayout footer;

  // Private variables used when creating the survey
  private Survey thisSurvey;
  private int typeOfQuestion;
  private static final int TEXT_QUESTION = 0;
  private static final int RADIO_QUESTION = 1;
  private static final int BOX_QUESTION = 2;
  private boolean hasChanges;

  private SurveyRepository surveyRepository;

  private CreateAlternative ca;

  public CreateSurveyView(SurveyRepository surveyRepository) {
    setId("createsurvey");

    this.surveyRepository = surveyRepository;
    thisSurvey = new Survey();
    hasChanges = false;

    header = new HorizontalLayout();
    questions = new VerticalLayout();
    addQuestionPackage = new VerticalLayout();
    footer = new HorizontalLayout();

    addQuestionButton = new Button("Add question", event -> addQuestion());
    submitSurveyButton = new Button("Submit survey", event -> saveSurvey());
    submitSurveyButton.setEnabled(false);
    cancelButton = new Button("Cancel", event -> getUI().ifPresent(ui -> ui.navigate("")));

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
    footer.add(submitSurveyButton);
    footer.add(cancelButton);

    surveyTitleTextField.addValueChangeListener(titleChange -> {
      checkFilledFields();
    });
    creatorNameTextField.addValueChangeListener(titleChange -> {
      checkFilledFields();
    });

    add(header);
    add(questions);
    add(addQuestionPackage);
    add(addQuestionButton);
    add(footer);

  }

  // Create addQuestion-package with listeners, if already created: save the question
  // Add handling for multiquestions
  public void addQuestion() {
    questionTitleTextField.focus();

    if (radioButtons != null) {

      if (typeOfQuestion == TEXT_QUESTION) {
        questions.add(new TextQuestionWithButtons(questionTitleTextField.getValue(), this));
      } else {
        questions.add(new RadioQuestionWithButtons(questionTitleTextField.getValue(), this,
            ca.getAlternativeList(), typeOfQuestion));
        addQuestionPackage.remove(ca);
      }
      refreshQuestions();
      if (ca != null) {
        ca.getAlternativeList().clear();
        ca = null;
        typeOfQuestion = 10;
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
      questionTitleTextField.setPlaceholder("Question");
      questionTitleTextField.setLabel("Question");
      addQuestionPackage.add(questionTitleTextField);
      addQuestionPackage.add(radioButtons);

      radioButtons.addValueChangeListener(event -> {
        if (event.getValue().equalsIgnoreCase("Text question")) {

          createQuestion(TEXT_QUESTION);
        } else if (event.getValue().equalsIgnoreCase("Radio Question")) {
          addQuestionButton.setEnabled(false);
          createQuestion(RADIO_QUESTION);
        } else if (event.getValue().equalsIgnoreCase("Checkbox Question")) {
          addQuestionButton.setEnabled(false);
          createQuestion(BOX_QUESTION);
        }
        if (questionTitleTextField.isEmpty()) {
          addQuestionButton.setEnabled(false);
        }
      });

      // Checks if the questionTitle and questionType is set
      questionTitleTextField.addValueChangeListener(event -> {
        if (event.getSource().getValue().length() > 255) {
          event.getSource().setValue(event.getSource().getValue().substring(0, 255));
          Notification.show("Question can max contain 255 characters");
        }
        if (questionTitleTextField.isEmpty() || radioButtons.getValue() == null) {

          addQuestionButton.setEnabled(false);

        } else if (radioButtons.getValue().equalsIgnoreCase("Text question")
            && !questionTitleTextField.getValue().isEmpty()) {

          createQuestion(TEXT_QUESTION);
        } else if ((radioButtons.getValue().equalsIgnoreCase("Radio Question")
            && !questionTitleTextField.getValue().isEmpty())) {
          addQuestionButton.setEnabled(false);
          createQuestion(RADIO_QUESTION);
        } else if ((radioButtons.getValue().equalsIgnoreCase("Checkbox Question")
            && !questionTitleTextField.getValue().isEmpty())) {
          addQuestionButton.setEnabled(false);
          createQuestion(BOX_QUESTION);
        }
      });
    }
    addQuestionButton.setEnabled(false);

  }

  public void createQuestion(int typeOfQuestion) {
    if (typeOfQuestion != this.typeOfQuestion && typeOfQuestion != TEXT_QUESTION) {

      if (ca == null) {
        ca = new CreateAlternative(typeOfQuestion, this);
        addQuestionPackage.add(ca);
      } else if (this.typeOfQuestion == TEXT_QUESTION) {
        ca.setTypeOfQuestion(typeOfQuestion);
        addQuestionPackage.add(ca);
      } else {
        ca.setTypeOfQuestion(typeOfQuestion);
      }
      this.typeOfQuestion = typeOfQuestion;
    } else if (typeOfQuestion == TEXT_QUESTION) {
      for (int i = 0; i < addQuestionPackage.getComponentCount(); i++) {
        Component c = addQuestionPackage.getComponentAt(i);
        if (c == ca) {
          addQuestionPackage.remove(ca);
        }
      }
      addQuestionButton.setEnabled(true);
      this.typeOfQuestion = typeOfQuestion;
    }
    if (ca != null) {
      if (!ca.getAlternativeList().isEmpty() && !questionTitleTextField.isEmpty()) {
        addQuestionButton.setEnabled(true);
      }
    }
  }

  // Move question in questions container
  public void moveQuestion(Button button, int moveDirection) {

    questions.replace(button.getParent().get().getParent().get(), questions.getComponentAt(
        questions.indexOf(button.getParent().get().getParent().get()) + moveDirection));
    hasChanges = true;
  }

  // Remove questions from questions container
  public void removeQuestion(Component c) {
    questions.remove(c);
    refreshQuestions();
    checkFilledFields();
  }

  // Edit question via pencil buttons in custom components
  public void editQuestion(Button button) {
    new EditDialog(button);
    hasChanges = true;
  }

  // Save survey with questions to database
  public void saveSurvey() {
    thisSurvey.getQuestions().clear();

    for (int position = 0; position < questions.getComponentCount(); position++) {

      if (questions.getComponentAt(position) instanceof TextQuestionWithButtons) {
        TextQuestionWithButtons component =
            (TextQuestionWithButtons) questions.getComponentAt(position);
        TextQuestion question = new TextQuestion();
        question.setTitle(component.getQuestion());
        question.setPosition(position);
        thisSurvey.getQuestions().add(question);

      } else if (questions.getComponentAt(position) instanceof RadioQuestionWithButtons) {
        RadioQuestionWithButtons component =
            (RadioQuestionWithButtons) questions.getComponentAt(position);
        MultiQuestion question = new MultiQuestion();
        question.setTitle(component.getQuestion());
        question.setPosition(position);
        question.setQuestionType(component.getQuestionType());
        question.getAlternatives().addAll(component.getAlternatives());

        thisSurvey.getQuestions().add(question);

      }

    }
    thisSurvey.setCreator(creatorNameTextField.getValue());
    thisSurvey.setTitle(surveyTitleTextField.getValue());
    thisSurvey.setDate(LocalDate.now());

    surveyRepository.save(thisSurvey);

    hasChanges = false;
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
    hasChanges = true;
  }

  /*
   * REMEMBER JONATHAN: DELETE ALL UNUSED METHODS IN THE VERTICALALYOUT CRAP THINGS
   */
  public void refreshQuestions() {
    for (int i = 0; i < questions.getComponentCount(); i++) {
      // questions.indexOf(button.getParent().get().getParent().get()) == 0
      // if (questions.getComponentAt(position) instanceof TextQuestionWithButtons) {
      if (questions.getComponentAt(i) instanceof TextQuestionWithButtons) {
        TextQuestionWithButtons component = (TextQuestionWithButtons) questions.getComponentAt(i);
        if (questions.getComponentCount() == 1) {
          component.getUpButton().setEnabled(false);
          component.getDownButton().setEnabled(false);
        } else if (questions.getComponentCount() > 1) {
          if (questions.indexOf(component) == 0) {
            component.getUpButton().setEnabled(false);
            component.getDownButton().setEnabled(true);
          } else if (questions.indexOf(component) == questions.getComponentCount() - 1) {
            component.getUpButton().setEnabled(true);
            component.getDownButton().setEnabled(false);
          } else {
            // These are probably just duplicates (scroll down)
            component.getUpButton().setEnabled(true);
            component.getDownButton().setEnabled(true);
          }
        } else {
          component.getUpButton().setEnabled(true);
          component.getDownButton().setEnabled(true);
        }
      } else if (questions.getComponentAt(i) instanceof RadioQuestionWithButtons) {
        RadioQuestionWithButtons component = (RadioQuestionWithButtons) questions.getComponentAt(i);
        if (questions.getComponentCount() == 1) {
          component.getUpButton().setEnabled(false);
          component.getDownButton().setEnabled(false);
        } else if (questions.getComponentCount() > 1) {
          if (questions.indexOf(component) == 0) {
            component.getUpButton().setEnabled(false);
            component.getDownButton().setEnabled(true);
          } else if (questions.indexOf(component) == questions.getComponentCount() - 1) {
            component.getUpButton().setEnabled(true);
            component.getDownButton().setEnabled(false);
          } else {
            // These are probably just duplicates and can be cleaned up but leaving this "for now".
            component.getUpButton().setEnabled(true);
            component.getDownButton().setEnabled(true);
          }
        } else {
          component.getUpButton().setEnabled(true);
          component.getDownButton().setEnabled(true);
        }
      }
    }
  }

  public void setLoadedQuestions() {
    for (int i = 0; i < questions.getComponentCount(); i++) {
      // questions.indexOf(button.getParent().get().getParent().get()) == 0
      // if (questions.getComponentAt(position) instanceof TextQuestionWithButtons) {
      if (questions.getComponentAt(i) instanceof TextQuestionWithButtons) {
        TextQuestionWithButtons component = (TextQuestionWithButtons) questions.getComponentAt(i);
        if (questions.getComponentCount() <= 1) {
          component.getUpButton().setEnabled(false);
          component.getDownButton().setEnabled(false);
        } else if (questions.getComponentCount() > 1) {
          if (questions.indexOf(component) == 0) {
            component.getUpButton().setEnabled(false);
          } else if (questions.indexOf(component) == questions.getComponentCount() - 1) {
            component.getUpButton().setEnabled(true);
            component.getDownButton().setEnabled(false);
          }
        }
      } else if (questions.getComponentAt(i) instanceof RadioQuestionWithButtons) {
        RadioQuestionWithButtons component = (RadioQuestionWithButtons) questions.getComponentAt(i);
        if (questions.getComponentCount() == 1) {
          component.getUpButton().setEnabled(false);
          component.getDownButton().setEnabled(false);
        } else if (questions.getComponentCount() > 1) {
          if (questions.indexOf(component) == 0) {
            component.getUpButton().setEnabled(false);
          } else if (questions.indexOf(component) == questions.getComponentCount() - 1) {
            component.getUpButton().setEnabled(true);
            component.getDownButton().setEnabled(false);
          }
        }
      }
    }
  }

  // HasUrlParameter function, if parameter is null, do nothing but load the view as normal.
  // If parameter has an surveyId, load questions, title and creator. Add Multiquestion
  @Override
  public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
    if (parameter != null) {
      thisSurvey = surveyRepository.getSurveyById(parameter);

      for (Question q : thisSurvey.getQuestions()) {
        surveyTitleTextField.setValue(thisSurvey.getTitle());
        creatorNameTextField.setValue(thisSurvey.getCreator());
        if (q instanceof TextQuestion) {
          questions.add(new TextQuestionWithButtons(q.getTitle(), this));
        } else {
          MultiQuestion mq = (MultiQuestion) q;

          List<String> stringAlternatives = new ArrayList<>();
          for (MultiQuestionAlternative mqa : mq.getAlternatives()) {
            stringAlternatives.add(mqa.getTitle());
          }

          questions.add(new RadioQuestionWithButtons(mq.getTitle(), this, stringAlternatives,
              mq.getQuestionType()));
        }
      }
      submitSurveyButton.setText("Save");
      setLoadedQuestions();
      thisSurvey.getQuestions().clear();
      checkFilledFields();
      hasChanges = false;
    }
  }

  public Button getAddQuestionButton() {
    return addQuestionButton;
  }

  public TextField getQuestionTitleTextField() {
    return questionTitleTextField;
  }

  @Override
  public void beforeLeave(BeforeLeaveEvent event) {
    if (hasChanges) {
      ContinueNavigationAction action = event.postpone();
      ConfirmDialog dialog = new ConfirmDialog(action, this);
      dialog.open();
    }
  }
}
