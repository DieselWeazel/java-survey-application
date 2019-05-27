package com.considlia.survey.ui;

import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.ui.custom_component.SurveyGrid;
import com.vaadin.flow.router.Route;

/**
 * Class to create the Home View
 */
@Route(value = "", layout = MainLayout.class)
public class HomeView extends BaseView {

  public HomeView(SurveyRepository surveyRepository) {
    super("Home");

    add(new SurveyGrid(surveyRepository));
  }
}
