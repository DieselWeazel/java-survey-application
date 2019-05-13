package com.considlia.survey.ui;

import com.considlia.survey.model.MultiQuestion;
import com.considlia.survey.model.Question;
import com.considlia.survey.model.Survey;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.ui.custom_component.ReadMultiQuestionLayout;
import com.considlia.survey.ui.custom_component.ReadTextQuestionLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
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

  // -- TextContainers --
  private H1 h1;

  // -- Buttons --
  private Button saveButton;

  // -- Serializement --
  private SurveyRepository surveyRepository;

  private Survey survey;

  // -- Layout --
  public ShowSurveyView(SurveyRepository surveyRepository) {
    // Using same ID as CreateSurveyView as of now.
    setId("createsurvey");
    this.surveyRepository = surveyRepository;
    this.h1 = new H1("PlaceHolder // Survey Not Actually Found, Text not Updated");
    this.saveButton = new Button();
    saveButton.setText("Send");
    saveButton.addClickListener(e -> saveResponse());

    initUI();
  }

  // -- UI method, adding, etc.
  private void initUI() {

    headerHorizontalLayout.setId("createheader");
    surveyVerticalLayout.setId("questionpackage");

    Label mandatoryLabel = new Label("* = Mandatory question");
    headerHorizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    headerHorizontalLayout.add(h1);
    add(headerHorizontalLayout, mandatoryLabel, surveyVerticalLayout);
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

        HorizontalLayout horLayout = new HorizontalLayout();

        ReadMultiQuestionLayout readMultiQuestionLayout = new ReadMultiQuestionLayout(mq);
        horLayout.add(readMultiQuestionLayout);
        surveyVerticalLayout.add(horLayout);
      } else {
        ReadTextQuestionLayout readTextQuestionLayout = new ReadTextQuestionLayout(q);

        surveyVerticalLayout.add(readTextQuestionLayout);
      }
    }

    surveyVerticalLayout.add(saveButton);

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
