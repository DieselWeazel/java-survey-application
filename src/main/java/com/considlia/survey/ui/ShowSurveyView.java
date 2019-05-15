package com.considlia.survey.ui;

import com.considlia.survey.model.MultiQuestion;
import com.considlia.survey.model.Question;
import com.considlia.survey.model.RatioQuestion;
import com.considlia.survey.model.Survey;
import com.considlia.survey.model.TextQuestion;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.ui.custom_component.ReadMultiQuestionLayout;
import com.considlia.survey.ui.custom_component.ReadRatioQuestionLayout;
import com.considlia.survey.ui.custom_component.ReadTextQuestionLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

/*
 * http://localhost:8080/showsurvey/1
 */
@StyleSheet("css/app.css")
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
  private Survey survey;
  private boolean containsMandatory = false;

  public ShowSurveyView(SurveyRepository surveyRepository) {
    // Using same ID as CreateSurveyView as of now.
    setId("createsurvey");
    this.surveyRepository = surveyRepository;
    this.h1 = new H1("PlaceHolder // Survey Not Actually Found, Text not Updated");
    this.h5 = new H5();
    this.saveButton = new Button();
    saveButton.setText("Send");
    saveButton.addClickListener(e -> saveResponse());
  }

  // -- UI method, adding, etc.
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
  }

  // -- Data methods --
  // -- Parameter Method, finding Survey --
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
        // Or Create SurveyNotFoundException
        h1.setText("ERROR, no Survey by the ID of: " + parameter + " exists.");

        goHome();
      }
    } catch (NullPointerException e) {
      h1.setText(e.getMessage());
    }
  }

  // -- Loading Survey to Layout
  public void loadSurvey(Survey survey) {

    for (Question q : survey.getQuestions()) {

      if (q instanceof MultiQuestion) {
        MultiQuestion mq = (MultiQuestion) q;

        ReadMultiQuestionLayout readMultiQuestionLayout = new ReadMultiQuestionLayout(mq);
        surveyVerticalLayout.add(readMultiQuestionLayout);
      } else if (q instanceof TextQuestion) {
        ReadTextQuestionLayout readTextQuestionLayout = new ReadTextQuestionLayout(q);
        surveyVerticalLayout.add(readTextQuestionLayout);
      } else if (q instanceof RatioQuestion) {
        RatioQuestion rq = (RatioQuestion) q;
        ReadRatioQuestionLayout ratioQuestionLayout = new ReadRatioQuestionLayout(rq);
        surveyVerticalLayout.add(ratioQuestionLayout);
      }

      if (q.isMandatory()) {
        containsMandatory = true;
      }
    }

    surveyVerticalLayout.add(saveButton);

    initUI();
  }

  // -- Public Button Methods --
  public void saveResponse() {
    saveButton.setText("Survey Response cannot be saved yet!");
  }

  public void goHome() {
    saveButton.setText("Go To Mainview");
    saveButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));
    add(saveButton);
  }
}
