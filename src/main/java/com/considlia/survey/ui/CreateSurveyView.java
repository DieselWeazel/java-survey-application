package com.considlia.survey.ui;

import com.considlia.survey.model.MultiQuestion;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.considlia.survey.model.Question;
import com.considlia.survey.model.QuestionFactory;
import com.considlia.survey.model.Role;
import com.considlia.survey.model.Survey;
import com.considlia.survey.model.TextQuestion;
import com.considlia.survey.model.User;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.ui.custom_component.ConfirmDialog;
import com.considlia.survey.ui.custom_component.CreateAlternative;
import com.considlia.survey.ui.custom_component.EditDialog;
import com.considlia.survey.ui.custom_component.QuestionType;
import com.considlia.survey.ui.custom_component.question_with_button.MultiQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.QuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.TextQuestionWithButtons;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveEvent.ContinueNavigationAction;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@StyleSheet("css/app.css")
@Route(value = "createsurvey", layout = MainLayout.class)
@Secured({Role.USER, Role.ADMIN})
public class CreateSurveyView extends BaseView
    implements HasUrlParameter<Long>, BeforeLeaveObserver {

  // Buttons
  private Button addQuestionButton;
  private Button submitSurveyButton;
  private Button cancelButton;
  private Select<String> selectOptions;
  private Checkbox mandatory;

  // Textfields
  private TextField surveyTitleTextField;
  private TextField creatorNameTextField;
  private TextField questionTitleTextField;
  private TextArea descriptionTextArea;

  // Containers
  private VerticalLayout header;
  private HorizontalLayout titleContainer;
  private VerticalLayout addQuestionContainer;
  private HorizontalLayout addQuestionHorizontalContainer;
  private VerticalLayout questions;

  // Private variables used when creating the survey
  private Survey thisSurvey;
  private boolean hasChanges;
  private QuestionType questionType;
  private SurveyRepository surveyRepository;
  private CreateAlternative createAlternative;

  @Autowired private UserRepository userRepository;

  public CreateSurveyView(SurveyRepository surveyRepository) {
    super("Create Survey");
    setId("createsurvey");

    this.surveyRepository = surveyRepository;
    thisSurvey = new Survey();
    hasChanges = false;

    initSurvey();
    initAddQuestionContainer();
    initLayout();
  }

  public void initSurvey() {
    header = new VerticalLayout();
    header.setId("createheader");
    titleContainer = new HorizontalLayout();
    titleContainer.setWidthFull();
    titleContainer.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

    addQuestionContainer = new VerticalLayout();
    addQuestionContainer.setId("questionpackage");
    addQuestionHorizontalContainer = new HorizontalLayout();
    addQuestionHorizontalContainer.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

    questions = new VerticalLayout();

    addQuestionButton = new Button("Add question", event -> addQuestion());
    addQuestionButton.setEnabled(false);
    submitSurveyButton = new Button("Submit survey", event -> saveSurvey());
    submitSurveyButton.setEnabled(false);
    cancelButton = new Button("Cancel", event -> getUI().ifPresent(ui -> ui.navigate("")));

    surveyTitleTextField = new TextField();
    creatorNameTextField = new TextField();
    descriptionTextArea = new TextArea();

    surveyTitleTextField.addValueChangeListener(
        titleChange -> {
          checkFilledFields();
        });
    creatorNameTextField.addValueChangeListener(
        titleChange -> {
          checkFilledFields();
        });

    surveyTitleTextField.setLabel("Survey title");
    surveyTitleTextField.setPlaceholder("Survey title");
    surveyTitleTextField.setWidth("400px");
    surveyTitleTextField.setValueChangeMode(ValueChangeMode.EAGER);
    surveyTitleTextField.setRequired(true);
    creatorNameTextField.setLabel("Created by");
    creatorNameTextField.setPlaceholder("Created by");
    creatorNameTextField.setWidth("250px");
    creatorNameTextField.setValueChangeMode(ValueChangeMode.EAGER);
    creatorNameTextField.setRequired(true);
    descriptionTextArea.setLabel("Description");
    descriptionTextArea.setWidth("600px");

    // Show User, just for show
    // TODO move me (separate method?)
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDetails) {
      String username = ((UserDetails) principal).getUsername();
      creatorNameTextField.setValue(username);
      creatorNameTextField.setEnabled(false);
      //      creatorNameTextField.setPlaceholder(username);

    } else {
      String username = principal.toString();
      creatorNameTextField.setPlaceholder(username);
    }
  }

  public void initAddQuestionContainer() {
    questionTitleTextField = new TextField();
    questionTitleTextField.setValueChangeMode(ValueChangeMode.EAGER);
    questionTitleTextField.setWidth("300px");
    questionTitleTextField.setPlaceholder("Question");
    questionTitleTextField.setLabel("Question");

    questionTitleTextField.addValueChangeListener(
        event -> {

          // checks if the the string contains more than 255 characters. If true cuts string after
          // index
          // 255
          if (event.getSource().getValue().length() > 255) {
            event.getSource().setValue(event.getSource().getValue().substring(0, 255));
            Notification.show("Question can max contain 255 characters");
          }

          if (questionTitleTextField.isEmpty() || selectOptions.getValue() == null) {
            addQuestionButton.setEnabled(false);
          } else if (selectOptions.getValue().equalsIgnoreCase("Text question")
              && !questionTitleTextField.getValue().isEmpty()) {
            userCreationQuestion(QuestionType.TEXT);
          } else if ((selectOptions.getValue().equalsIgnoreCase("Radio Question")
              && !questionTitleTextField.getValue().isEmpty())) {
            userCreationQuestion(QuestionType.RADIO);
          } else if ((selectOptions.getValue().equalsIgnoreCase("Checkbox Question")
              && !questionTitleTextField.getValue().isEmpty())) {
            userCreationQuestion(QuestionType.CHECKBOX);
          }
        });

    selectOptions = new Select<>();
    selectOptions.setPlaceholder("Type of question");
    selectOptions.setItems("Text question", "Radio Question", "Checkbox Question");
    selectOptions.addValueChangeListener(
        event -> {
          if (event.getValue().equalsIgnoreCase("Text question")) {
            userCreationQuestion(QuestionType.TEXT);
          } else if (event.getValue().equalsIgnoreCase("Radio Question")) {
            userCreationQuestion(QuestionType.RADIO);
          } else if (event.getValue().equalsIgnoreCase("Checkbox Question")) {
            userCreationQuestion(QuestionType.CHECKBOX);
          }
          if (questionTitleTextField.isEmpty()) {
            addQuestionButton.setEnabled(false);
          }
        });

    mandatory = new Checkbox("Mandatory Question");
  }

  public void initLayout() {
    titleContainer.add(
        surveyTitleTextField, creatorNameTextField, submitSurveyButton, cancelButton);
    header.add(titleContainer);
    header.add(descriptionTextArea);

    addQuestionHorizontalContainer.add(questionTitleTextField, addQuestionButton, mandatory);
    addQuestionContainer.add(addQuestionHorizontalContainer, selectOptions);

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
        questions.add(
            new TextQuestionWithButtons(
                questionTitleTextField.getValue(), this, mandatory.getValue()));
        break;
      case RADIO:
        questions.add(
            new MultiQuestionWithButtons(
                questionTitleTextField.getValue(),
                this,
                createAlternative.getAlternativeList(),
                QuestionType.RADIO,
                mandatory.getValue()));
        addQuestionContainer.remove(createAlternative);
        break;
      case CHECKBOX:
        questions.add(
            new MultiQuestionWithButtons(
                questionTitleTextField.getValue(),
                this,
                createAlternative.getAlternativeList(),
                QuestionType.CHECKBOX,
                mandatory.getValue()));
        addQuestionContainer.remove(createAlternative);
        break;
    }

    updateMoveButtonStatus();
    if (createAlternative != null) {
      createAlternative.getAlternativeList().clear();
      createAlternative = null;
      questionType = null;
    }

    questionTitleTextField.setValue("");
    selectOptions.setValue("");
    checkFilledFields();

    addQuestionContainer.setVisible(true);
    addQuestionButton.setEnabled(false);
  }

  public void userCreationQuestion(QuestionType questionType) {

    // if questionType was previously radio and now check and the other way around
    if (questionType != this.questionType && questionType != QuestionType.TEXT) {

      /*
       * For every new question created createAlternative will be null the first time
       * userCreationQuestion() is invoked
       */
      if (createAlternative == null) {
        createAlternative = new CreateAlternative(questionType, this);
        addQuestionContainer.add(createAlternative);
      }

      /*
       * if the previous questionType was a textquestion the alternative textfields must be
       * displayed.
       */
      else if (this.questionType == QuestionType.TEXT) {
        createAlternative.setQuestionType(questionType);
        addQuestionContainer.add(createAlternative);
      }

      /*
       * If just switching between radio and check, nothing in the gui has to change. Just the
       * questionType in createAlternative for Question to have the right type when addQuestion() is
       * invoked.
       */
      else {
        createAlternative.setQuestionType(questionType);
      }
    } else if (questionType == QuestionType.TEXT) {
      if (verticalLayoutContainsComponent(addQuestionContainer, createAlternative)) {
        addQuestionContainer.remove(createAlternative);
      }

      addQuestionButton.setEnabled(true);
    }

    // regarding of what happens this.questionType will be updated
    this.questionType = questionType;

    if (createAlternative != null) {
      if (!createAlternative.getAlternativeList().isEmpty() && !questionTitleTextField.isEmpty()) {
        addQuestionButton.setEnabled(true);
      } else if ((createAlternative.getAlternativeList().isEmpty()
              || questionTitleTextField.isEmpty())
          && questionType != QuestionType.TEXT) {
        addQuestionButton.setEnabled(false);
      }
    }
  }

  // Move question in questions container
  public void moveQuestion(Button button, int moveDirection) {

    questions.replace(
        button.getParent().get().getParent().get(),
        questions.getComponentAt(
            questions.indexOf(button.getParent().get().getParent().get()) + moveDirection));
    hasChanges = true;
  }

  // Remove questions from questions container
  public void removeQuestion(Component c) {
    questions.remove(c);
    updateMoveButtonStatus();
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
    QuestionFactory qf = new QuestionFactory();

    for (int position = 0; position < questions.getComponentCount(); position++) {
      Question question = qf.createQuestion(questions.getComponentAt(position), position);
      thisSurvey.getQuestions().add(question);
    }

    thisSurvey.setCreator(creatorNameTextField.getValue());
    thisSurvey.setTitle(surveyTitleTextField.getValue());
    thisSurvey.setDescription(descriptionTextArea.getValue());
    thisSurvey.setDate(LocalDate.now());

    /*
    TODO org.springframework.dao.InvalidDataAccessApiUsageException: detached entity passed to persist:
    TODO find solution to this, relationship isnt working as of now
    We now save Survey to the User instead.
     */
    User user = userRepository.findByUsername(creatorNameTextField.getValue());
    //    user.getSurveys().add(thisSurvey);

    thisSurvey.setUser(user);
    surveyRepository.save(thisSurvey);
    //    userRepository.save(user);

    hasChanges = false;
    getUI().ifPresent(ui -> ui.navigate(""));
  }

  // Control if questions list is not 0
  public boolean validateQuestionListLength() {
    return questions.getComponentCount() != 0;
  }

  // Check if title and creator textfields are filled out
  public boolean checkFilledFields() {
    hasChanges = true;
    if (!(surveyTitleTextField.isEmpty() || creatorNameTextField.isEmpty())
        && validateQuestionListLength()) {
      submitSurveyButton.setEnabled(true);
      return true;
    } else {
      submitSurveyButton.setEnabled(false);
      return false;
    }
  }

  public void updateMoveButtonStatus() {
    for (int i = 0; i < questions.getComponentCount(); i++) {
      QuestionWithButtons component = (QuestionWithButtons) questions.getComponentAt(i);
      component.getUpButton().setEnabled(true);
      component.getDownButton().setEnabled(true);

      if (i == 0) {
        component.getUpButton().setEnabled(false);
      }
      if (i == questions.getComponentCount() - 1) {
        component.getDownButton().setEnabled(false);
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
        descriptionTextArea.setValue(thisSurvey.getDescription());
        if (q instanceof TextQuestion) {
          questions.add(new TextQuestionWithButtons(q.getTitle(), this, q.isMandatory()));
        } else {
          MultiQuestion mq = (MultiQuestion) q;

          List<String> stringAlternatives = new ArrayList<>();
          for (MultiQuestionAlternative mqa : mq.getAlternatives()) {
            stringAlternatives.add(mqa.getTitle());
          }
          questions.add(
              new MultiQuestionWithButtons(
                  mq.getTitle(), this, stringAlternatives, mq.getQuestionType(), mq.isMandatory()));
        }
      }
      submitSurveyButton.setText("Save Survey");
      updateMoveButtonStatus();
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

  public boolean verticalLayoutContainsComponent(VerticalLayout vLayout, Component component) {
    for (int i = 0; i < vLayout.getComponentCount(); i++) {
      Component c = addQuestionContainer.getComponentAt(i);
      if (c == component) {
        return true;
      }
    }

    return false;
  }
}
