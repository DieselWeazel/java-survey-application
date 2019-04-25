package com.considLia.survey.ui;

import com.considLia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "showsurvey", layout = MainLayout.class)
public class ShowSurveyView extends VerticalLayout implements HasUrlParameter<Long> {

  private H1 h1;

  public ShowSurveyView(SurveyRepository surveyRepository) {


    add(h1);
  }

  @Override
  public void setParameter(BeforeEvent event, Long parameter) {
    if (parameter == null) {
      h1.setText("ERROR, NOT FOUND, create exception.");
    }
  }
}
