package com.considlia.survey.ui;

import com.considlia.survey.model.Survey;
import com.considlia.survey.model.SurveyResponse;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.repositories.ResponseRepository;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.security.CustomUserService;
import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.custom_component.showsurveycomponents.ShowQuestionComponent;
import com.considlia.survey.ui.custom_component.showsurveycomponents.ShowQuestionFactory;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.considlia.survey.ui.custom_component.showsurveycomponents.SurveyLoader;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  Designated View for loading Surveys. Takes a parameter being the Survey ID
 *  Each survey loaded generates a set of components.
 *
 *  To gather response, every component implements the interface
 *  ShowQuestionComponent, which has the method GatherResponse. Call this
 *  method for each component when gathering all answers to the Survey.
 *  Link:
 *  http://localhost:8080/showsurvey/1
 *  written by: Jonathan Harr
 */
@Route(value = "showsurvey", layout = MainLayout.class)
public class ShowSurveyView extends BaseView implements HasUrlParameter<Long> {

  // -- Private Variables --
  // -- Containers --
  private HorizontalLayout headerHorizontalLayout = new HorizontalLayout();
  private VerticalLayout surveyVerticalLayout = new VerticalLayout();

  private H1 h1;
  private H5 h5;
  private Button saveButton;
  private SurveyRepository surveyRepository;
  private ResponseRepository responseRepository;
  private Survey survey;
  private boolean containsMandatory = false;

  // Factory stuff (Fix me)
  private ShowQuestionFactory showQuestionFactory;

  private List<ShowQuestionComponent> readQuestionList = new ArrayList<>();

  @Autowired
  private CustomUserService customUserService;
  /**
   * Constructs view.
   *
   * @param surveyRepository for loading Surveys.
   * @param responseRepository for storing SurveyResponse.
   * @param surveyLoader factory loading Questions and Answerform.
   */
  public ShowSurveyView(
      SurveyRepository surveyRepository,
      ResponseRepository responseRepository,
      SurveyLoader surveyLoader) {
    this.surveyRepository = surveyRepository;
    this.responseRepository = responseRepository;
    this.showQuestionFactory = surveyLoader;
    this.h1 = new H1("PlaceHolder // Survey Not Actually Found, Text not Updated");
    this.h5 = new H5();
    this.saveButton = new Button();
    saveButton.setText("Send");
    //    saveButton.addClickListener(e -> saveResponse());
  }

  /** initiates page GUI. */
  private void initUI() {

    headerHorizontalLayout.setId("createheader");
    surveyVerticalLayout.setId("questionpackage");
    h1.setMinWidth("70%");

    headerHorizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    headerHorizontalLayout.setVerticalComponentAlignment(Alignment.CENTER, h5);
    headerHorizontalLayout.add(h1, h5);

    if (containsMandatory) {
      Label mandatoryLabel = new Label("* = Mandatory question");
      add(headerHorizontalLayout, mandatoryLabel, surveyVerticalLayout);
    } else {
      add(headerHorizontalLayout, surveyVerticalLayout);
    }
    add(saveButton);
  }

  // TODO change to !parameter ==null, reduce boilerplate code.
  /**
   * Checks if page is meant to load a Survey, if parameter is null, sets text and button into
   * having not found a Survey.
   *
   * @param event initiates before load.
   * @param parameter ID of Survey, value determining event.
   */
  @Override
  public void setParameter(BeforeEvent event, Long parameter) {
    try {
      if (parameter == null) {
        // Null Pointer Exception? (Survey Null Pointer Exception)
        h1.setText("ERROR, no survey ID parameter given.");
        goHome();
      }
      if (surveyRepository.findById(parameter).isPresent()) {
        survey = surveyRepository.getSurveyById(parameter);
        h1.setText(survey.getTitle());
        h5.setText(survey.getDescription());
        loadSurvey(survey);
      } else {
        h1.setText("ERROR, no Survey by the ID of: " + parameter + " exists.");
        goHome();
      }
    } catch (NullPointerException e) {
      h1.setText(e.getMessage());
    }
  }

  /**
   * Loads Questions,
   *
   * @param survey, reads all questions inside Survey, for each question, adds a ShowQuestionLayout
   *     via ShowQuestionFactory into surveyVerticalLayout and each to readQuestionList.
   */
  public void loadSurvey(Survey survey) {
    for (Question question : survey.getQuestions()) {
      ShowQuestionLayout showQuestionLayout =
          (ShowQuestionLayout) showQuestionFactory.loadQuestionLayout(question);
      surveyVerticalLayout.add(showQuestionLayout);
      readQuestionList.add((ShowQuestionComponent) showQuestionLayout);
    }
    saveButton.addClickListener(
        e -> {
          try {
            saveResponse();
          } catch (ValidationException e1) {
            e1.printStackTrace();
          }
        });
    initUI();
  }

  /**
   * Creates a SurveyResponse and stores all Answer(s). For each readQuestionComponent inside
   * readQuestionList, gatherResponse() method is called storing each Answer into respective Bean,
   * connecting them to each Question of origin. Saves SurveyResponse to ResponseRepository.
   *
   * @throws ValidationException
   */
  public void saveResponse() throws ValidationException {
    SurveyResponse surveyResponse = new SurveyResponse();

    // Gathers responses from each component and adds them to our list.
    readQuestionList.forEach(e-> {
      try {
        surveyResponse.addAnswer(e.gatherResponse());
      } catch (ValidationException e1) {
        e1.printStackTrace();
      }
    });
    // If User is logged in, User is stored, else not.
    if (SecurityUtils.isUserLoggedIn()) {
      surveyResponse.setUser(customUserService.getUser());
    }
    // Connect SurveyResponse to Survey.
    surveyResponse.setSurvey(survey);
    responseRepository.save(surveyResponse);
    UI.getCurrent().navigate("");
  }

  /**
   * If no survey is loaded, saveButton changes function, offers a way back to homeview for User.
   */
  public void goHome() {
    saveButton.setText("Go To Mainview");
    saveButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));
    add(saveButton);
  }
}
