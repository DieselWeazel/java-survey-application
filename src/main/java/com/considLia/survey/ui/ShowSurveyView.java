package com.considLia.survey.ui;

import com.considLia.survey.model.MultiQuestionAlternative;
import com.considLia.survey.model.Question;
import com.considLia.survey.model.Survey;
import com.considLia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import javax.validation.constraints.Null;

@Route(value = "showsurvey", layout = MainLayout.class)
public class ShowSurveyView extends VerticalLayout implements HasUrlParameter<Long> {

  // -- Private Variables --
  private SurveyRepository surveyRepository;

  // -- TextContainers --
  private H1 h1;

  // -- Serializement --
  private Binder<Question> binder = new Binder<>(Question.class);

  // -- Layout --
  public ShowSurveyView(SurveyRepository surveyRepository) {
    this.surveyRepository = surveyRepository;
    h1 = new H1("lol");

    add(h1);
  }

  // -- Parameter Method, finding Survey --
  @Override
  public void setParameter(BeforeEvent event, Long parameter) {
    try {
      if (parameter == null) {
        // Null Pointer Exception? (Survey Null Pointer Exception)
        h1.setText("ERROR, no survey ID parameter given.");
      }
      if (surveyRepository.findById(parameter).isPresent()) {
        Survey survey = surveyRepository.getSurveyBySurveyId(parameter);
        h1.setText(survey.getSurveyTitle());
      loadSurvey(survey);
      } else {
        // Or Create SurveyNotFoundException
        h1.setText("ERROR, no Survey by the ID of: " + parameter + " exists.");
      }
    } catch (NullPointerException e){
      h1.setText(e.getMessage());
    }
  }

  // -- Loading Survey to Layout
  public void loadSurvey(Survey survey) {
//    for (Question q : survey.getQuestionList()) {
//      add(new H1(q.getQuestionTitle()));
//      add(new TextField());
//      if (q.getAlternativeList() != null) {
//        add(new H1(q.getQuestionTitle()));
//
//        for (MultiQuestionAlternative mqa : q.getAlternativeList()) {
//          add(new H1(mqa.getAlternativeTitle()));
//        }
//      }
//    }

    // java.lang.IllegalArgumentException: Could not resolve property name Test textfråga from Property set for bean com.considLia.survey.model.Survey
    // Bean issue?

    TextField textField = new TextField();

    for (Question q: survey.getQuestionList()){
      binder.forField(textField).bind(q.getQuestionTitle());

    }
    binder.bindInstanceFields(this);
//    binder.setBean(survey.getQuestionList());




  }
}

