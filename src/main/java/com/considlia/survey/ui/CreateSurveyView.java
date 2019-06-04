package com.considlia.survey.ui;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.Role;
import com.considlia.survey.model.Survey;
import com.considlia.survey.model.SurveyStatus;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.security.CustomUserService;
import com.considlia.survey.ui.custom_component.ConfirmDialog;
import com.considlia.survey.ui.custom_component.CreateAlternative;
import com.considlia.survey.ui.custom_component.CreateRatioComponents;
import com.considlia.survey.ui.custom_component.CreateTextComponents;
import com.considlia.survey.ui.custom_component.EditDialog;
import com.considlia.survey.ui.custom_component.QuestionFactory;
import com.considlia.survey.ui.custom_component.QuestionWithButtonsFactory;
import com.considlia.survey.ui.custom_component.question_with_button.QuestionWithButtons;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
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

/**
 * Handles everything regarding creating or editing a {@link Survey}
 *
 */
@Route(value = "createsurvey", layout = MainLayout.class)
@Secured({Role.USER, Role.ADMIN})
public class CreateSurveyView extends BaseView
    implements HasUrlParameter<Long>, BeforeLeaveObserver {

  // Buttons
  private Button addQuestionButton;
  private Button submitSurveyButton;
  private Button cancelButton;
  private Select<QuestionType> selectOptions;
  private Checkbox mandatory;

  // Textfields
  private TextField surveyTitleTextField;
  private TextField questionTitleTextField;
  private TextArea descriptionTextArea;

  // Containers
  private VerticalLayout header;
  private HorizontalLayout titleContainer;
  private VerticalLayout addQuestionContainer;
  private HorizontalLayout addQuestionHorizontalContainer;
  private VerticalLayout questions;
  private VerticalLayout extraComponents;

  // Private variables used when creating the survey
  private Survey thisSurvey;
  private boolean hasChanges;
  private QuestionType questionType;
  private SurveyRepository surveyRepository;
  private CreateAlternative createAlternative;
  private CreateRatioComponents createRatioComponents;
  private CreateTextComponents createTextComponents;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CustomUserService customUserService;

  /**
   * Constructor for CreateSurveyView
   *
   * @param surveyRepository
   * @param customUserService
   */
  public CreateSurveyView(SurveyRepository surveyRepository, CustomUserService customUserService) {
    super("Create Survey");

    this.surveyRepository = surveyRepository;
    this.customUserService = customUserService;
    thisSurvey = new Survey();
    hasChanges = false;

    initSurvey();
    initAddQuestionContainer();
    initLayout();
  }

  /**
   * Initiates the view for CreateSurvey and creates events for surveyTitleTextField,
   * creatorNameTextField, descriptionTextArea.
   */
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
    extraComponents = new VerticalLayout();

    addQuestionButton = new Button("Add question", event -> addQuestion());
    addQuestionButton.setEnabled(false);
    submitSurveyButton = new Button("Submit survey", event -> saveSurvey());
    submitSurveyButton.setEnabled(false);
    cancelButton = new Button("Cancel", event -> getUI().ifPresent(ui -> ui.navigate("")));

    surveyTitleTextField = createTextField("400px", "Survey Title", true);
    descriptionTextArea = new TextArea();

    surveyTitleTextField.addValueChangeListener(titleChange -> {
      // validates textField value with validateString
      titleChange.getSource().setValue(validateString(titleChange.getSource().getValue(), 70));
      checkFilledFields();
    });
    descriptionTextArea.addValueChangeListener(descChange -> {
      hasChanges = true;
      // validates textField value with validateString
      descChange.getSource().setValue(validateString(descChange.getSource().getValue(), 1000));
    });

    descriptionTextArea.setLabel("Description");
    descriptionTextArea.setWidth("600px");
    descriptionTextArea.setValueChangeMode(ValueChangeMode.EAGER);
  }

  /**
   * adds layouts to containers, invoked by constructor.
   */
  public void initLayout() {
    titleContainer.add(surveyTitleTextField, submitSurveyButton, cancelButton);
    header.add(titleContainer);
    header.add(descriptionTextArea);

    addQuestionHorizontalContainer.add(questionTitleTextField, addQuestionButton, mandatory);
    addQuestionContainer.add(addQuestionHorizontalContainer, selectOptions, extraComponents);

    add(header);
    add(addQuestionContainer);
    add(questions);
  }

  /**
   * Creates and sets events for questionTitleTextField {@link TextField} and selectOptions
   * {@link Select}. Events invokes userCreationQuestion with value QuestionType. Also creates
   * mandatory {@link Checkbox}.
   */
  public void initAddQuestionContainer() {
    questionTitleTextField = createTextField("300px", "Question", false);
    questionTitleTextField.addValueChangeListener(event -> {

      // validates textField value with validateString
      event.getSource().setValue(validateString(event.getSource().getValue(), 255));

      if (questionTitleTextField.isEmpty() || selectOptions.getValue() == null) {
        addQuestionButton.setEnabled(false);
      } else if (selectOptions.getValue() == QuestionType.TEXTFIELD
          && !questionTitleTextField.getValue().isEmpty()) {
        userCreationQuestion(QuestionType.TEXTFIELD);
      } else if ((selectOptions.getValue() == QuestionType.RADIO
          && !questionTitleTextField.getValue().isEmpty())) {
        userCreationQuestion(QuestionType.RADIO);
      } else if ((selectOptions.getValue() == QuestionType.CHECKBOX
          && !questionTitleTextField.getValue().isEmpty())) {
        userCreationQuestion(QuestionType.CHECKBOX);
      } else if ((selectOptions.getValue() == QuestionType.RATIO
          && !questionTitleTextField.getValue().isEmpty())) {
        userCreationQuestion(QuestionType.RATIO);
      }
    });

    selectOptions = new Select<>();
    selectOptions.setLabel("Type of question : ");
    selectOptions.setItems(QuestionType.TEXTFIELD, QuestionType.RADIO, QuestionType.CHECKBOX,
        QuestionType.RATIO);
    selectOptions.setValue(QuestionType.TEXTFIELD);
    selectOptions.addValueChangeListener(event -> {
      questionType = selectOptions.getValue();
      if (event.getValue() == QuestionType.TEXTFIELD) {
        userCreationQuestion(QuestionType.TEXTFIELD);
      } else if (event.getValue() == QuestionType.RADIO) {
        userCreationQuestion(QuestionType.RADIO);
      } else if (event.getValue() == QuestionType.CHECKBOX) {
        userCreationQuestion(QuestionType.CHECKBOX);
      } else if (event.getValue() == QuestionType.RATIO) {
        userCreationQuestion(QuestionType.RATIO);
      }
      if (questionTitleTextField.isEmpty()) {
        addQuestionButton.setEnabled(false);
      }
    });

    createTextComponents = new CreateTextComponents(this);
    extraComponents.add(createTextComponents);
    mandatory = new Checkbox("Mandatory Question");
  }

  /**
   * Creates a new textfield from the parameters, valueChangeMode is set to EAGER
   * 
   * @param width of the textfield
   * @param placeholderAndLabel
   * @param isRequired
   * @return TextField
   */
  public TextField createTextField(String width, String placeholderAndLabel, boolean isRequired) {
    TextField textField = new TextField();
    textField.setWidth(width);
    textField.setRequired(isRequired);
    textField.setLabel(placeholderAndLabel);
    textField.setPlaceholder(placeholderAndLabel);
    textField.setValueChangeMode(ValueChangeMode.EAGER);

    return textField;
  }

  /**
   * Removes all questions, then adds all Questions with new index values. Invokes
   * {@link updateMoveButtonStatus()}
   */
  public void refreshItemsInGUI() {
    setPositions();
    questions.removeAll();
    extraComponents.removeAll();

    for (Question q : thisSurvey.getQuestions()) {
      questions.add(QuestionWithButtonsFactory.create(q, this));
    }
    updateMoveButtonStatus();
  }

  /**
   * Set each questions position depending on its index in the list
   */
  public void setPositions() {
    for (Question q : thisSurvey.getQuestions()) {
      q.setPosition(thisSurvey.getQuestions().indexOf(q));
    }
  }

  /**
   * Adds question to {@link Survey} object. Then clears components and variables used in
   * addQuestionContainer
   */
  public void addQuestion() {
    questionTitleTextField.focus();
    questionType = selectOptions.getValue();

    if (questionType == QuestionType.TEXTFIELD) {
      // this because the radiobutton value is a string and not enum...
      if (createTextComponents.getRadioButtons().getValue().equals("Textarea")) {
        questionType = QuestionType.TEXTAREA;
      }
    }

    thisSurvey
        .addQuestion(QuestionFactory.createQuestion(questionType, questionTitleTextField.getValue(),
            mandatory.getValue(), createAlternative.getAlternativeList(), createRatioComponents));

    refreshItemsInGUI();

    questionTitleTextField.setValue("");
    selectOptions.setValue(QuestionType.TEXTFIELD);

    extraComponents.removeAll();
    createTextComponents = new CreateTextComponents(this);
    createTextComponents.getRadioButtons().setValue("Textfield");
    extraComponents.add(createTextComponents);

    createAlternative = null;
    createRatioComponents = null;
    questionType = null;
    checkFilledFields();

    addQuestionContainer.setVisible(true);
    addQuestionButton.setEnabled(false);
    mandatory.setValue(false);
  }

  /**
   * Updated components in extraComponents Layout for given QuestionType.
   *
   * @param questionType
   */
  public void userCreationQuestion(QuestionType questionType) {
    // Clears the extraComponentLayout
    extraComponents.removeAll();
    initExtraComponents();

    if (questionType == QuestionType.RATIO) {
      extraComponents.add(createRatioComponents);
    } else if (questionType != QuestionType.TEXTFIELD) {
      createAlternative.setQuestionType(questionType);
      extraComponents.add(createAlternative);
    } else if (questionType == QuestionType.TEXTFIELD) {
      extraComponents.add(createTextComponents);
    }

    changeBtn();
  }

  /**
   * Creates instance of componentsCreate START HERESTART HERESTART HERESTART HERESTART HEREType.
   * This is done here because components are set to null after a Question is saved.
   */
  public void initExtraComponents() {
    if (createRatioComponents == null) {
      createRatioComponents = new CreateRatioComponents(this);
    }
    if (createAlternative == null) {
      createAlternative = new CreateAlternative(this);
    }
    if (createTextComponents == null) {
      createTextComponents = new CreateTextComponents(this);
    }
  }

  /**
   * Manages setEnable for addQuestionButton {@link Button}
   */
  public void changeBtn() {
    questionType = selectOptions.getValue();
    switch (questionType) {
      case TEXTFIELD:
        addQuestionButton.setEnabled(!questionTitleTextField.isEmpty()
            && createTextComponents.getRadioButtons().getValue() != null);
        break;
      case RATIO:
        addQuestionButton.setEnabled(createRatioComponents.limitsContainsOnlyLetters()
            && !createRatioComponents.isLimitEmpty() && !questionTitleTextField.isEmpty());

        break;
      case RADIO:
      case CHECKBOX:
        if (questionType == QuestionType.RADIO
            && createAlternative.getAlternativeList().size() <= 1) {
          addQuestionButton.setEnabled(false);
        } else {
          Set<String> set = new LinkedHashSet<>();
          set.addAll(createAlternative.getAlternativeList());
          addQuestionButton.setEnabled(
              !createAlternative.getAlternativeList().isEmpty() && !questionTitleTextField.isEmpty()
                  && createAlternative.getAlternativeList().size() == set.size());
        }
        break;
      default:
        addQuestionButton.setEnabled(false);
    }
  }

  /**
   * Manages changing position of questions.
   *
   * @param question {@link Question}
   * @param moveDirection 1 = down, -1 = up
   */
  public void moveQuestion(Question question, int moveDirection) {
    thisSurvey.getQuestions().remove(question);
    thisSurvey.getQuestions().add(question.getPosition() + moveDirection, question);

    refreshItemsInGUI();
    hasChanges = true;
  }

  /**
   * Removes question from questions {@link List} in {@link Survey}. Invoked from
   * {@link ConfirmDialog}
   *
   * @param question type: {@link Question}
   */
  public void removeQuestion(Question question) {
    thisSurvey.getQuestions().remove(question);

    refreshItemsInGUI();
    checkFilledFields();
  }

  /**
   *
   * @param button
   */
  public void editQuestion(Button button) {
    new EditDialog(button);
    hasChanges = true;
  }

  /**
   * Sets the surveys title, creator, description, user (questions is already set) and saves it and
   * then reroute back to homeView
   */
  public void saveSurvey() {
    thisSurvey.setCreator(customUserService.getUser().getLastName() + ", "
        + customUserService.getUser().getFirstName());
    thisSurvey.setTitle(surveyTitleTextField.getValue());
    thisSurvey.setDescription(descriptionTextArea.getValue());
    thisSurvey.setDate(LocalDate.now());
    thisSurvey.setStatus(SurveyStatus.EDITABLE);

    thisSurvey.setUser(customUserService.getUser());
    surveyRepository.save(thisSurvey);
    hasChanges = false;
    navigateToSuccessView(ConfirmSuccessView.SURVEY_CREATED_STRING);
  }

  /**
   * Manages setEnable for submitSurveyButton {@link Button}
   *
   * @returns true, if: surveyTitleTextField has content, creatorNameTextField has content and
   *          {@link Survey} contains {@link Question}s. else returns false
   */
  public boolean checkFilledFields() {
    hasChanges = true;
    if (!(surveyTitleTextField.isEmpty()) && questions.getComponentCount() != 0) {
      submitSurveyButton.setEnabled(true);
      return true;
    } else {
      submitSurveyButton.setEnabled(false);
      return false;
    }
  }

  /**
   * Disables the up button if the question is the first one. DIsbales the down button if the
   * question is the last one.
   */
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

  /**
   * Sets the title, the creator and the description of the Survey
   */
  @Override
  public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
    if (parameter != null) {
      thisSurvey = surveyRepository.getSurveyById(parameter);

      setTitle("Editing Survey");

      surveyTitleTextField.setValue(thisSurvey.getTitle());
      descriptionTextArea.setValue(thisSurvey.getDescription());
      refreshItemsInGUI();

      createTextComponents = new CreateTextComponents(this);
      createTextComponents.getRadioButtons().setValue("Textfield");
      extraComponents.add(createTextComponents);

      submitSurveyButton.setText("Save Survey");
      updateMoveButtonStatus();
      checkFilledFields();
      hasChanges = false;
    }
  }

  /**
   *
   * @returns addQuestionButton
   */
  public Button getAddQuestionButton() {
    return addQuestionButton;
  }

  /**
   *
   * @returns questionTitleTextField type: {@link TextField}
   */
  public TextField getQuestionTitleTextField() {
    return questionTitleTextField;
  }

  /**
   * Creates dialog to confirm exit if there are unsaved changes to confirm exit.
   */
  @Override
  public void beforeLeave(BeforeLeaveEvent event) {
    if (hasChanges) {
      ContinueNavigationAction action = event.postpone();
      ConfirmDialog dialog = new ConfirmDialog(action, this);
      dialog.open();
    }
  }

  /**
   * If string is longer than desired it is trimmed down to desired length.
   * 
   * @param string
   * @param stringMaxLength
   * @returns string with valid length
   */
  public String validateString(String string, int stringMaxLength) {
    if (string.length() > stringMaxLength) {
      string = string.substring(0, stringMaxLength);
      Notification.show("Textfield can contain maximum " + stringMaxLength + " characters");
    }
    return string;
  }
}
