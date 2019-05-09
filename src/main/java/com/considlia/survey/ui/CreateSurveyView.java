package com.considlia.survey.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.considlia.survey.custom_component.ConfirmDialog;
import com.considlia.survey.custom_component.CreateAlternative;
import com.considlia.survey.custom_component.EditDialog;
import com.considlia.survey.custom_component.QuestionType;
import com.considlia.survey.custom_component.RadioQuestionWithButtons;
import com.considlia.survey.custom_component.TextQuestionWithButtons;
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
  private HorizontalLayout addQuestionHorizontalContainer;
  private VerticalLayout addQuestionContainer;
  private VerticalLayout questions;

  // Private variables used when creating the survey
  private Survey thisSurvey;
  private boolean hasChanges;
  private QuestionType questionType;
  private SurveyRepository surveyRepository;
  private CreateAlternative createAlternative;

  public CreateSurveyView(SurveyRepository surveyRepository) {
    setId("createsurvey");

    this.surveyRepository = surveyRepository;
    thisSurvey = new Survey();
    hasChanges = false;

    initSurvey();
    initAddQuestionContainer();
    initLayout();
  }

  public void initSurvey() {
    header = new HorizontalLayout();
    header.setWidthFull();
    header.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    header.setClassName("createheader");

    questions = new VerticalLayout();
    addQuestionContainer = new VerticalLayout();
    addQuestionContainer.setClassName("questionpackage");
    addQuestionHorizontalContainer = new HorizontalLayout();
    addQuestionHorizontalContainer.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

    addQuestionButton = new Button("Add question", event -> addQuestion());
    addQuestionButton.setEnabled(false);
    submitSurveyButton = new Button("Submit survey", event -> saveSurvey());
    submitSurveyButton.setEnabled(false);
    cancelButton = new Button("Cancel", event -> getUI().ifPresent(ui -> ui.navigate("")));

    surveyTitleTextField = new TextField();
    creatorNameTextField = new TextField();

    surveyTitleTextField.addValueChangeListener(titleChange -> {
      checkFilledFields();
    });
    creatorNameTextField.addValueChangeListener(titleChange -> {
      checkFilledFields();
    });

    surveyTitleTextField.setLabel("Survey title");
    surveyTitleTextField.setPlaceholder("Survey title");
    surveyTitleTextField.setWidth("400px");
    surveyTitleTextField.setValueChangeMode(ValueChangeMode.EAGER);
    creatorNameTextField.setLabel("Created by");
    creatorNameTextField.setPlaceholder("Created by");
    creatorNameTextField.setWidth("250px");
    creatorNameTextField.setValueChangeMode(ValueChangeMode.EAGER);
  }

  public void initAddQuestionContainer() {
    questionTitleTextField = new TextField();
    questionTitleTextField.setValueChangeMode(ValueChangeMode.EAGER);
    questionTitleTextField.setWidth("300px");
    questionTitleTextField.setPlaceholder("Question");
    questionTitleTextField.setLabel("Question");

    questionTitleTextField.addValueChangeListener(event -> {
      if (event.getSource().getValue().length() > 255) {
        event.getSource().setValue(event.getSource().getValue().substring(0, 255));
        Notification.show("Question can max contain 255 characters");
      }
      if (questionTitleTextField.isEmpty() || radioButtons.getValue() == null) {
        addQuestionButton.setEnabled(false);
      } else if (radioButtons.getValue().equalsIgnoreCase("Text question")
          && !questionTitleTextField.getValue().isEmpty()) {
        createQuestion(QuestionType.TEXT);
      } else if ((radioButtons.getValue().equalsIgnoreCase("Radio Question")
          && !questionTitleTextField.getValue().isEmpty())) {
        createQuestion(QuestionType.RADIO);
      } else if ((radioButtons.getValue().equalsIgnoreCase("Checkbox Question")
          && !questionTitleTextField.getValue().isEmpty())) {
        createQuestion(QuestionType.CHECKBOX);
      }
    });

    radioButtons = new RadioButtonGroup<>();
    radioButtons.setItems("Text question", "Radio Question", "Checkbox Question");
    radioButtons.addValueChangeListener(event -> {
      if (event.getValue().equalsIgnoreCase("Text question")) {
        createQuestion(QuestionType.TEXT);
      } else if (event.getValue().equalsIgnoreCase("Radio Question")) {
        createQuestion(QuestionType.RADIO);
      } else if (event.getValue().equalsIgnoreCase("Checkbox Question")) {
        createQuestion(QuestionType.CHECKBOX);
      }
      if (questionTitleTextField.isEmpty()) {
        addQuestionButton.setEnabled(false);
      }
    });
  }

  public void initLayout() {
    header.add(surveyTitleTextField, creatorNameTextField);
    header.add(submitSurveyButton);
    header.add(cancelButton);

    addQuestionHorizontalContainer.add(questionTitleTextField, addQuestionButton);
    addQuestionContainer.add(addQuestionHorizontalContainer, radioButtons);

    add(header);
    add(addQuestionContainer);
    add(questions);
  }

  // Create addQuestion-package with listeners, if already created: save the question
  // Add handling for multiquestions
  public void addQuestion() {
    questionTitleTextField.focus();

    switch (questionType) {
      case TEXT:
        questions.add(new TextQuestionWithButtons(questionTitleTextField.getValue(), this));
        break;
      case RADIO:
        questions.add(new RadioQuestionWithButtons(questionTitleTextField.getValue(), this,
            createAlternative.getAlternativeList(), QuestionType.RADIO));
        addQuestionContainer.remove(createAlternative);
        break;
      case CHECKBOX:
        questions.add(new RadioQuestionWithButtons(questionTitleTextField.getValue(), this,
            createAlternative.getAlternativeList(), QuestionType.CHECKBOX));
        addQuestionContainer.remove(createAlternative);
        break;
    }

    refreshQuestions();
    if (createAlternative != null) {
      createAlternative.getAlternativeList().clear();
      createAlternative = null;
      questionType = null;
    }

    questionTitleTextField.setValue("");
    radioButtons.setValue("");
    checkFilledFields();

    addQuestionContainer.setVisible(true);
    addQuestionButton.setEnabled(false);
  }

  public void createQuestion(QuestionType questionType) {
    if (questionType != this.questionType && questionType != QuestionType.TEXT) {

      if (createAlternative == null) {
        createAlternative = new CreateAlternative(questionType, this);
        addQuestionContainer.add(createAlternative);
      } else if (this.questionType == QuestionType.TEXT) {
        createAlternative.setQuestionType(questionType);
        addQuestionContainer.add(createAlternative);
      } else {
        createAlternative.setQuestionType(questionType);
      }
      this.questionType = questionType;
    } else if (questionType == QuestionType.TEXT) {
      for (int i = 0; i < addQuestionContainer.getComponentCount(); i++) {
        Component c = addQuestionContainer.getComponentAt(i);
        if (c == createAlternative) {
          addQuestionContainer.remove(createAlternative);
        }
      }
      addQuestionButton.setEnabled(true);
      this.questionType = questionType;
    }
    if (createAlternative != null) {
      if (!createAlternative.getAlternativeList().isEmpty() && !questionTitleTextField.isEmpty()) {
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
