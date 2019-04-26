package com.considLia.survey.ui;

import com.considLia.survey.model.MultiQuestionAlternative;
import com.considLia.survey.model.Question;
import com.considLia.survey.model.Survey;
import com.considLia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

  // -- Only For Use With Testing, Delete when Merging --
  private Grid<Survey> grid = new Grid<>(Survey.class);

  public MainView(SurveyRepository surveyRepository) {
    // -- Only For Use With Testing, Delete when Merging --
    add(new H1("Survey List"));
    List<Survey> surveyList = surveyRepository.findAll();

    grid.setColumns("surveyTitle", "creator", "date");
    grid.setItems(surveyList);
    grid.addComponentColumn(survey -> {
      String route = UI.getCurrent().getRouter()
              .getUrl(ShowSurveyView.class, survey.getSurveyId());
      return new Anchor(route, "Details");
    });
    // -- Only For Use With Testing, Delete when Merging --

    add(grid);
    Survey survey = surveyRepository.getSurveyBySurveyId(1l);

    add(new H1(survey.getSurveyTitle() + " " + survey.getCreator() + " " + survey.getDate()));

    survey.getQuestionList();

    for (Question q : survey.getQuestionList()) {
      add(new H2(q.getQuestionTitle()));
      if (q.getAlternativeList() != null) {
        for (MultiQuestionAlternative mqa : q.getAlternativeList()) {
          add(new H3(mqa.getAlternativeTitle()));
        }
      }
    }

  }

}
