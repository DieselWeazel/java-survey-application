package com.considlia.survey.ui.UserViews;

import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.considlia.survey.ui.custom_component.SurveyGrid;
import com.vaadin.flow.router.Route;

@Route(value = "profileview", layout = MainLayout.class)
public class MyProfileView extends BaseView {

  private SurveyGrid surveyGrid;

  private SurveyRepository surveyRepository;

  public MyProfileView(SurveyRepository surveyRepository){
    super("Profile");
    this.surveyRepository = surveyRepository;
    surveyGrid = new SurveyGrid(this.getClass(), surveyRepository);
    add(surveyGrid);
  }
}
