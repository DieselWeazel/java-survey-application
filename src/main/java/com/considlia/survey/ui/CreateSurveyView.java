package com.considlia.survey.ui;

import com.considlia.survey.custom_component.CreateAlternative;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

  private CreateAlternative ca;

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
        refreshQuestions();
      } else {
        questions.add(new RadioQuestionWithButtons(questionTitleTextField.getValue(), this,
            ca.getAlternativeList(), typeOfQuestion));
        addQuestionPackage.remove(ca);
        refreshQuestions();
      }
      try {
        ca.getAlternativeList().clear();
        ca = null;
        typeOfQuestion = 10;
      } catch (NullPointerException e) {
        System.out.println("NullPointerException error caught");
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
          refreshQuestions();
        } else if (event.getValue().equalsIgnoreCase("Radio Question")) {
          addQuestionButton.setEnabled(false);
          createQuestion(RADIO_QUESTION);
          refreshQuestions();
        } else if (event.getValue().equalsIgnoreCase("Checkbox Question")) {
          addQuestionButton.setEnabled(false);
          createQuestion(BOX_QUESTION);
          refreshQuestions();
        }
        if (questionTitleTextField.isEmpty()) {
          addQuestionButton.setEnabled(false);
        }
      });

      // Checks if the questionTitle and questionType is set
      questionTitleTextField.addValueChangeListener(event -> {
        if (questionTitleTextField.isEmpty() || radioButtons.getValue() == null) {

          addQuestionButton.setEnabled(false);

        } else if (radioButtons.getValue().equalsIgnoreCase("Text question")
            && !questionTitleTextField.getValue().isEmpty()) {

          createQuestion(TEXT_QUESTION);
          refreshQuestions();
        } else if ((radioButtons.getValue().equalsIgnoreCase("Radio Question")
            && !questionTitleTextField.getValue().isEmpty())) {
          addQuestionButton.setEnabled(false);
          createQuestion(RADIO_QUESTION);
          refreshQuestions();
        } else if ((radioButtons.getValue().equalsIgnoreCase("Checkbox Question")
            && !questionTitleTextField.getValue().isEmpty())) {
          addQuestionButton.setEnabled(false);
          createQuestion(BOX_QUESTION);
          refreshQuestions();
        }
      });
    }
    addQuestionButton.setEnabled(false);
//    refreshQuestions();

  }

  public void createQuestion(int typeOfQuestion) {
    if (typeOfQuestion != this.typeOfQuestion && typeOfQuestion != TEXT_QUESTION) {

      try {
        addQuestionPackage.remove(ca);
      } catch (NullPointerException e) {
        System.out.println("NullPointerException error caught");
      }

      ca = new CreateAlternative(typeOfQuestion, this);
      addQuestionPackage.add(ca);
      // addQuestionButton.setEnabled(true);
      this.typeOfQuestion = typeOfQuestion;

    } else if (typeOfQuestion == TEXT_QUESTION) {
      try {
        addQuestionPackage.remove(ca);
      } catch (NullPointerException e) {
        System.out.println("NullPointerException error caught");
      }
      addQuestionButton.setEnabled(true);
      this.typeOfQuestion = typeOfQuestion;
    }
  }

  // Move question in questionscontainer
  public void moveQuestion(Button button, int moveDirection) {

    if (questions.indexOf(button.getParent().get().getParent().get()) == 0 && moveDirection == -1
        || questions.indexOf(
        button.getParent().get().getParent().get()) == questions.getComponentCount() - 1
        && moveDirection == 1) {
      // Do nothing
      refreshQuestions();
      return;
    } else {
      questions.replace(button.getParent().get().getParent().get(), questions.getComponentAt(
          questions.indexOf(button.getParent().get().getParent().get()) + moveDirection));
      refreshQuestions();
    }

  }

  // Remove questions from questionscontainer
  public void removeQuestion(Component c) {
    questions.remove(c);
    refreshQuestions();
    checkFilledFields();
  }

  // Edit question via pencil buttons in custom components
  public void editQuestion(Button button) {
    Dialog dialog = new Dialog();
    Button confirm = new Button("Confirm");
    TextField newTitleTextField = new TextField();

    dialog.open();
    dialog.add(newTitleTextField);
    if (button.getParent().get().getParent().get() instanceof TextQuestionWithButtons) {
      TextQuestionWithButtons choosenQuestion =
          (TextQuestionWithButtons) button.getParent().get().getParent().get();

      newTitleTextField.setValue(choosenQuestion.getQuestion());

      confirm.addClickListener(event -> {
        choosenQuestion.setQuestion(newTitleTextField.getValue());
        dialog.close();
      });

    } else {
      RadioQuestionWithButtons choosenQuestion =
          (RadioQuestionWithButtons) button.getParent().get().getParent().get();
      //
      newTitleTextField.setValue(choosenQuestion.getQuestion());

      VerticalLayout v = new VerticalLayout();
      for (String s : choosenQuestion.getStringAlternatives()) {
        System.out.println(s.toString());
        TextField alternative = new TextField();
        alternative.setValue(s);
        v.add(alternative);
      }
      dialog.add(v);
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

  /*
  REMEMBER JONATHAN:
  DELETE ALL UNUSED METHODS IN THE VERTICALALYOUT CRAP THINGS
   */
  public void refreshQuestions() {
    for (int i = 0; i < questions.getComponentCount(); i++) {
//      questions.indexOf(button.getParent().get().getParent().get()) == 0
//      if (questions.getComponentAt(position) instanceof TextQuestionWithButtons) {
      if (questions.getComponentAt(i) instanceof TextQuestionWithButtons) {
        TextQuestionWithButtons component =
            (TextQuestionWithButtons) questions.getComponentAt(i);
        if (questions.getComponentCount() <= 1) {
          component.getUpButton().setEnabled(false);
          component.getDownButton().setEnabled(false);
        } else if (questions.getComponentCount() > 1) {
          if (questions.indexOf(component) == 0) {
            component.getUpButton().setEnabled(false);
            component.getDownButton().setEnabled(true);
          } else if (questions.indexOf(component) == questions.getComponentCount() - 1) {
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
        RadioQuestionWithButtons component =
            (RadioQuestionWithButtons) questions.getComponentAt(i);
        if (questions.getComponentCount() == 1) {
          component.getUpButton().setEnabled(false);
          component.getDownButton().setEnabled(false);
        } else if (questions.getComponentCount() > 1) {
          if (questions.indexOf(component) == 0) {
            component.getUpButton().setEnabled(false);
            component.getDownButton().setEnabled(true);
          } else if (questions.indexOf(component) == questions.getComponentCount() - 1) {
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
//      questions.indexOf(button.getParent().get().getParent().get()) == 0
//      if (questions.getComponentAt(position) instanceof TextQuestionWithButtons) {
      if (questions.getComponentAt(i) instanceof TextQuestionWithButtons) {
        TextQuestionWithButtons component =
            (TextQuestionWithButtons) questions.getComponentAt(i);
        if (questions.getComponentCount() <= 1) {
          component.getUpButton().setEnabled(false);
          component.getDownButton().setEnabled(false);
        } else if (questions.getComponentCount() > 1) {
          if (questions.indexOf(component) == 0) {
            component.getUpButton().setEnabled(false);
          } else if (questions.indexOf(component) == questions.getComponentCount() - 1) {
            component.getDownButton().setEnabled(false);
          }
        }
      } else if (questions.getComponentAt(i) instanceof RadioQuestionWithButtons) {
        RadioQuestionWithButtons component =
            (RadioQuestionWithButtons) questions.getComponentAt(i);
        if (questions.getComponentCount() == 1) {
          component.getUpButton().setEnabled(false);
          component.getDownButton().setEnabled(false);
        } else if (questions.getComponentCount() > 1) {
          if (questions.indexOf(component) == 0) {
            component.getUpButton().setEnabled(false);
          } else if (questions.indexOf(component) == questions.getComponentCount() - 1) {
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
          MultiQuestion mq = (MultiQuestion) q;

          List<String> stringAlternatives = new ArrayList<>();
          for (MultiQuestionAlternative mqa : mq.getAlternativeList()) {
            stringAlternatives.add(mqa.getAlternativeTitle());
          }

          questions.add(new RadioQuestionWithButtons(mq.getQuestionTitle(), this,
              stringAlternatives, mq.getQuestionType()));
        }
      }
      submitSurveyButton.setText("Save");
      setLoadedQuestions();
      thisSurvey.getQuestionList().clear();
      checkFilledFields();
    }

  }

  public Button getAddQuestionButton() {
    return addQuestionButton;
  }

  public TextField getQuestionTitleTextField() {
    return questionTitleTextField;
  }
}
