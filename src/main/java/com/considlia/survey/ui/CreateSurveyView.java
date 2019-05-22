package com.considlia.survey.ui;

import com.considlia.survey.security.CustomUserService;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.MultiQuestionAlternative;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.Role;
import com.considlia.survey.model.Survey;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.ui.custom_component.ConfirmDialog;
import com.considlia.survey.ui.custom_component.CreateAlternative;
import com.considlia.survey.ui.custom_component.CreateRatioComponents;
import com.considlia.survey.ui.custom_component.CreateTextComponents;
import com.considlia.survey.ui.custom_component.EditDialog;
import com.considlia.survey.ui.custom_component.QuestionFactory;
import com.considlia.survey.ui.custom_component.QuestionWithButtonsFactory;
import com.considlia.survey.ui.custom_component.question_with_button.QuestionWithButtons;
import com.vaadin.flow.component.Component;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

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
  private TextField creatorNameTextField;
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

  public CreateSurveyView(SurveyRepository surveyRepository, CustomUserService customUserService) {
    super("Create Survey");
    // setId("createsurvey");

    this.surveyRepository = surveyRepository;
    this.customUserService = customUserService;
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
    extraComponents = new VerticalLayout();

    addQuestionButton = new Button("Add question", event -> addQuestion());
    addQuestionButton.setEnabled(false);
    submitSurveyButton = new Button("Submit survey", event -> saveSurvey());
    submitSurveyButton.setEnabled(false);
    cancelButton = new Button("Cancel", event -> getUI().ifPresent(ui -> ui.navigate("")));

    surveyTitleTextField = createTextField("400px", "Survey Title", true);
    creatorNameTextField = createTextField("250px", "Created By", true);
    descriptionTextArea = new TextArea();

    surveyTitleTextField.addValueChangeListener(titleChange -> {
      checkFilledFields();
    });
    creatorNameTextField.addValueChangeListener(creatorChange -> {
      checkFilledFields();
    });
    descriptionTextArea.addValueChangeListener(descChange -> {
      hasChanges = true;
    });

    descriptionTextArea.setLabel("Description");
    descriptionTextArea.setWidth("600px");

    /*
    Currently doesn't allow for editing of Users name within CreatorName for Survey.
     */
    creatorNameTextField.setValue(customUserService.getUser().getLastName() + ", " + customUserService.getUser().getFirstName());
    creatorNameTextField.setEnabled(false);
    descriptionTextArea.setValueChangeMode(ValueChangeMode.EAGER);
  }

  public void initAddQuestionContainer() {
    questionTitleTextField = createTextField("300px", "Question", false);
    questionTitleTextField.addValueChangeListener(event -> {

      // checks if the the string contains more than 255 characters. If true cuts string after index
      // 255
      if (event.getSource().getValue().length() > 255) {
        event.getSource().setValue(event.getSource().getValue().substring(0, 255));
        Notification.show("Question can max contain 255 characters");
      }

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
    selectOptions.setPlaceholder("Type of question");
    selectOptions.setItems(QuestionType.TEXTFIELD, QuestionType.RADIO, QuestionType.CHECKBOX,
        QuestionType.RATIO);
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

    mandatory = new Checkbox("Mandatory Question");
  }

  public void initLayout() {
    titleContainer.add(surveyTitleTextField, creatorNameTextField, submitSurveyButton,
        cancelButton);
    header.add(titleContainer);
    header.add(descriptionTextArea);

    addQuestionHorizontalContainer.add(questionTitleTextField, addQuestionButton, mandatory);
    addQuestionContainer.add(addQuestionHorizontalContainer, selectOptions, extraComponents);

    add(header);
    add(addQuestionContainer);
    add(questions);
  }

  public TextField createTextField(String width, String placeholderAndLabel, boolean isRequired) {
    TextField textField = new TextField();
    textField.setWidth(width);
    textField.setRequired(isRequired);
    textField.setLabel(placeholderAndLabel);
    textField.setPlaceholder(placeholderAndLabel);
    textField.setValueChangeMode(ValueChangeMode.EAGER);

    return textField;
  }

  public void refreshItems() {
    setIndex();
    questions.removeAll();
    extraComponents.removeAll();

    for (Question q : thisSurvey.getQuestions()) {
      questions.add(QuestionWithButtonsFactory.create(q, this));
    }
    updateMoveButtonStatus();
  }

  public void setIndex() {
    for (Question q : thisSurvey.getQuestions()) {
      q.setPosition(thisSurvey.getQuestions().indexOf(q));
    }
  }

  // Create addQuestion-package with listeners, if already created: save the question
  // Add handling for multiquestions
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

    refreshItems();

    createAlternative = null;
    createRatioComponents = null;
    createTextComponents = null;
    questionType = null;

    questionTitleTextField.setValue("");
    selectOptions.clear();
    checkFilledFields();

    addQuestionContainer.setVisible(true);
    addQuestionButton.setEnabled(false);
    mandatory.setValue(false);
  }

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

  public void initExtraComponents() {
    if (createRatioComponents == null) {
      createRatioComponents = new CreateRatioComponents(this);
    }
    if (createAlternative == null) {
      createAlternative = new CreateAlternative(questionType, this);
    }
    if (createTextComponents == null) {
      createTextComponents = new CreateTextComponents(this);
    }
  }

  public void changeBtn() {
    questionType = selectOptions.getValue();
    switch (questionType) {
      case TEXTFIELD:
        addQuestionButton.setEnabled(!questionTitleTextField.isEmpty()
            && createTextComponents.getRadioButtons().getValue() != null);
        break;
      case RATIO:
        addQuestionButton
            .setEnabled(!createRatioComponents.isLimitEmpty() && !questionTitleTextField.isEmpty());

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

  // Move question in questions container
  public void moveQuestion(Button button, int moveDirection) {
    QuestionWithButtons qb = (QuestionWithButtons) button.getParent().get().getParent().get();
    int currentIndex = thisSurvey.getQuestions().indexOf(qb.getQuestion());
    thisSurvey.getQuestions().remove(qb.getQuestion());
    thisSurvey.getQuestions().add(currentIndex + moveDirection, qb.getQuestion());

    refreshItems();
    hasChanges = true;
  }

  // Remove questions from questions container
  public void removeQuestion(Component c) {
    QuestionWithButtons qb = (QuestionWithButtons) c;
    thisSurvey.getQuestions().remove(qb.getQuestion());

    refreshItems();
    checkFilledFields();
  }

  // Edit question via pencil buttons in custom components
  public void editQuestion(Button button) {
    new EditDialog(button);
    hasChanges = true;
  }

  // Save survey with questions to database
  public void saveSurvey() {
    thisSurvey.setCreator(creatorNameTextField.getValue());
    thisSurvey.setTitle(surveyTitleTextField.getValue());
    thisSurvey.setDescription(descriptionTextArea.getValue());
    thisSurvey.setDate(LocalDate.now());

    thisSurvey.setUser(customUserService.getUser());
    surveyRepository.save(thisSurvey);
    hasChanges = false;
    getUI().ifPresent(ui -> ui.navigate(""));
  }

  // Check if title and creator textfields are filled out
  public boolean checkFilledFields() {
    hasChanges = true;
    if (!(surveyTitleTextField.isEmpty() || creatorNameTextField.isEmpty())
        && questions.getComponentCount() != 0) {
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

      surveyTitleTextField.setValue(thisSurvey.getTitle());
      creatorNameTextField.setValue(thisSurvey.getCreator());
      descriptionTextArea.setValue(thisSurvey.getDescription());
      refreshItems();

      submitSurveyButton.setText("Save Survey");
      updateMoveButtonStatus();
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
