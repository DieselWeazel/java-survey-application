package com.considlia.survey.ui;

import com.considlia.survey.model.Survey;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.repositories.ResponseRepository;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.ui.custom_component.ReadMultiQuestionLayout;
import com.considlia.survey.ui.custom_component.ReadRatioQuestionLayout;
import com.considlia.survey.ui.custom_component.ReadTextQuestionLayout;
import com.vaadin.flow.component.button.Button;
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

  public ShowSurveyView(SurveyRepository surveyRepository, ResponseRepository responseRepository) {
    this.surveyRepository = surveyRepository;
    this.responseRepository = responseRepository;
    this.h1 = new H1("PlaceHolder // Survey Not Actually Found, Text not Updated");
    this.h5 = new H5();
    this.saveButton = new Button();
    saveButton.setText("Send");
//    saveButton.addClickListener(e -> saveResponse());
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
    add(saveButton);
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
    initUI();
  }

  // -- Public Button Methods --
  // Demo för hur ett svar kan kunnas skicka in.
//  public void saveResponse() {
//
//    SurveyResponses sr = new SurveyResponses();
//    sr.setSurveyId(survey.getId());
//    sr.setDate(LocalDate.now());
//
//    for (int position = 0; position < surveyVerticalLayout.getComponentCount(); position++) {
//      ReadQuestionLayout component =
//          (ReadQuestionLayout) surveyVerticalLayout.getComponentAt(position);
//      switch (component.getQuestion().getQuestionType()) {
//        case TEXTFIELD:
//          ReadTextQuestionLayout castTextComponent = (ReadTextQuestionLayout) component;
//          sr.addAnswer(new Answers(castTextComponent.getQuestionField().getValue(),
//              castTextComponent.getQuestion()));
//          break;
//        case TEXTAREA:
//          ReadTextQuestionLayout castTextAreaComponent = (ReadTextQuestionLayout) component;
//          sr.addAnswer(new Answers(castTextAreaComponent.getQuestionField().getValue(),
//              castTextAreaComponent.getQuestion()));
//          break;
//        case RADIO:
//          ReadMultiQuestionLayout castRadioComponent = (ReadMultiQuestionLayout) component;
//          sr.addAnswer(new Answers(castRadioComponent.getRadioButtons().getValue().getTitle(),
//              castRadioComponent.getQuestion()));
//          break;
//        case CHECKBOX:
//          ReadMultiQuestionLayout castCheckboxComponent = (ReadMultiQuestionLayout) component;
//          for (Iterator<MultiQuestionAlternative> i =
//              castCheckboxComponent.getCheckBoxButtons().getValue().iterator(); i.hasNext();) {
//            sr.addAnswer(new Answers(i.next().getTitle(), castCheckboxComponent.getQuestion()));
//          }
//          break;
//        case RATIO:
//          ReadRatioQuestionLayout castRatioComponent = (ReadRatioQuestionLayout) component;
//          sr.addAnswer(new Answers(castRatioComponent.getRadioButtons().getValue().substring(0, 1),
//              castRatioComponent.getQuestion()));
//          break;
//        default:
//          break;
//      }
//    }
//    responseRepository.save(sr);
//
//    for (SurveyResponses srPrint : responseRepository.findAllBySurveyId(survey.getId())) {
//      for (Answers aPrint : srPrint.getAnswers()) {
//        System.out.println(aPrint.getQuestion().getTitle() + ": " + aPrint.getAnswer());
//      }
//      System.out.println();
//    }
//    getUI().ifPresent(ui -> ui.navigate(""));
//  }

  public void goHome() {
    saveButton.setText("Go To Mainview");
    saveButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));
    add(saveButton);
  }
}
