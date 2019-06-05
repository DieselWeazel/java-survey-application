package com.considlia.survey.ui;

import com.considlia.survey.repositories.ResponseRepository;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.security.CustomUserService;
import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.custom_component.SurveyGrid;
import com.vaadin.flow.router.Route;

/**
 * Class to create the Home View
 */
@Route(value = "", layout = MainLayout.class)
public class HomeView extends BaseView {

  public HomeView(SurveyRepository surveyRepository, CustomUserService customUserService,
      ResponseRepository responseRepository) {
    super("Home");

    if (SecurityUtils.isUserLoggedIn()) {
      add(new SurveyGrid(surveyRepository, customUserService, responseRepository));
    } else {
      add(new SurveyGrid(surveyRepository));
    }
  }
}
