package com.considlia.survey.ui;

import com.considlia.survey.model.Survey;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.ui.custom_component.SurveyGrid;
import com.vaadin.flow.router.Route;
import java.util.List;

@Route(value = "", layout = MainLayout.class)
public class HomeView extends BaseView {

  private SurveyGrid surveyGrid;

  private SurveyRepository surveyRepository;

  public HomeView(SurveyRepository surveyRepository) {
    super("Home");
    this.surveyRepository = surveyRepository;

    surveyGrid = new SurveyGrid(this.getClass(), surveyRepository);
    add(surveyGrid);
  }
}
