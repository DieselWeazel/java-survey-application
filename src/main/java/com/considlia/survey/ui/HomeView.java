package com.considlia.survey.ui;

import com.considlia.survey.ui.custom_component.SurveyGrid;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.considlia.survey.model.Survey;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.ui.custom_component.ConfirmDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class HomeView extends BaseView {

  private SurveyGrid surveyGrid;
  private List<Survey> surveyList;

  private SurveyRepository surveyRepository;

  public HomeView(SurveyRepository surveyRepository) {
    super("Home");
    this.surveyRepository = surveyRepository;
    surveyGrid = new SurveyGrid(this.getClass(), surveyRepository);
    add(surveyGrid);
  }
}
