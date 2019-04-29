package com.considLia.survey.ui;

import java.util.ArrayList;
import java.util.List;
import com.considLia.survey.custom_component.ConfirmDialog;
import com.considLia.survey.model.Survey;
import com.considLia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

  private SurveyRepository surveyRepository;

  public MainView(SurveyRepository surveyRepository) {
    this.surveyRepository = surveyRepository;

    List<Survey> surveyList = new ArrayList<>();

    // Hämtar lista från Databasen
    surveyList = surveyRepository.findAll();

    Grid<Survey> grid = new Grid<>();
    grid.addColumn(Survey::getSurveyId).setHeader("Id");
    grid.addColumn(Survey::getSurveyTitle).setHeader("Title");
    grid.addColumn(Survey::getCreator).setHeader("Creator");
    grid.addColumn(Survey::getDate).setHeader("Date");
    grid.addComponentColumn(item -> showButtons(grid, item));

    if (surveyList.isEmpty()) {
      grid.setHeight("80px");
    }
    grid.setItems(surveyList);

    add(grid);
  }

  public HorizontalLayout showButtons(Grid<Survey> grid, Survey item) {
    Button showSurvey = new Button(new Icon(VaadinIcon.EYE));
    Button editSurvey = new Button(new Icon(VaadinIcon.PENCIL));
    Button deleteSurvey = new Button(new Icon(VaadinIcon.TRASH), onDelete -> {
      ConfirmDialog confirmDialog = new ConfirmDialog("Confirm delete",
          "Are you sure you want to delete the item?", surveyRepository, grid, item);
    });

    return new HorizontalLayout(showSurvey, editSurvey, deleteSurvey);
  }
}
