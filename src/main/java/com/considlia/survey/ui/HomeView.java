package com.considlia.survey.ui;

import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.custom_component.SurveyGrid;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class HomeView extends BaseView {

  private SurveyGrid surveyGrid;

  private SurveyRepository surveyRepository;

  private Button loginButton;
  private Button registerButton;
  private HorizontalLayout horizontalLayout;

  public HomeView(SurveyRepository surveyRepository) {
    super("Home");
    this.surveyRepository = surveyRepository;
    this.loginButton = new Button("Login");
    this.registerButton = new Button("Register");
    loginButton.addClickListener(e -> UI.getCurrent().navigate("login"));
    registerButton.addClickListener(e -> UI.getCurrent().navigate("registration"));
    this.horizontalLayout = new HorizontalLayout(loginButton, registerButton);
    if (!SecurityUtils.isUserLoggedIn()) {
      add(horizontalLayout);
    }

    surveyGrid = new SurveyGrid(this.getClass(), surveyRepository);
    add(surveyGrid);
  }
}
