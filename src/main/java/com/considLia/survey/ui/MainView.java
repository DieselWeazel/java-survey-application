package com.considLia.survey.ui;

import java.util.ArrayList;
import java.util.List;
import com.considLia.survey.model.Survey;
import com.considLia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

  public MainView(SurveyRepository surveyRepository) {

    List<Survey> surveyList = new ArrayList<>();

    // Hämtar lista från Databasen
    surveyList = surveyRepository.findAll();

    Grid<Survey> grid = new Grid<>();
    Grid.Column<Survey> idColumn = grid.addColumn(Survey::getSurveyId).setHeader("Id");
    Grid.Column<Survey> titleColumn = grid.addColumn(Survey::getSurveyTitle).setHeader("Title");
    Grid.Column<Survey> creatorColumn = grid.addColumn(Survey::getCreator).setHeader("Creator");
    Grid.Column<Survey> dateColumng = grid.addColumn(Survey::getDate).setHeader("Date");

    if (surveyList.isEmpty()) {
      grid.setHeight("80px");
    }
    grid.setItems(surveyList);

    add(grid);
  }
}
