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


    if (!surveyList.isEmpty()) {
      Grid<Survey> grid = new Grid<>();
      Grid.Column<Survey> idColumn = grid.addColumn(Survey::getSurveyId).setHeader("Id");
      Grid.Column<Survey> titleColumn = grid.addColumn(Survey::getSurveyTitle).setHeader("Title");
      Grid.Column<Survey> creatorColumn = grid.addColumn(Survey::getCreator).setHeader("Creator");
      Grid.Column<Survey> dateColumng = grid.addColumn(Survey::getDate).setHeader("Date");
      grid.setItems(surveyList);
      add(grid);

    }

    else {
      Grid<Survey> grid = new Grid<>();
      grid.setHeight("80px");
      Grid.Column<Survey> idColumn = grid.addColumn(Survey::getSurveyId).setHeader("Id");
      Grid.Column<Survey> titleColumn = grid.addColumn(Survey::getSurveyTitle).setHeader("Title");
      Grid.Column<Survey> creatorColumn = grid.addColumn(Survey::getCreator).setHeader("Creator");
      Grid.Column<Survey> dateColumng = grid.addColumn(Survey::getDate).setHeader("Date");
      add(grid);

    }
    // TextField id = new TextField("ID");
    // TextField title = new TextField("Survey title");
    // TextField creator = new TextField("Survey creator");
    // TextField date = new TextField("Date");
    //
    // FormLayout form = new FormLayout(id, title, creator, date);
    //
    //
    //
    // add(form);



    // Binder<Person> binder = new Binder<>(Person.class);
    // binder.bind(firstName, Person::getFirstName, Person::setFirstName);
    // binder.bind(lastName, Person::getLastName, Person::setLastName);

    // return new BinderCrudEditor<>(binder, form);

    // Crud<Survey> crud = new Crud<>(Survey.class, createPersonEditor());
    //
    // PersonDataProvider dataProvider = new PersonDataProvider();
    //
    // crud.getGrid().removeColumnByKey("id");
    // crud.setDataProvider(dataProvider);
    // crud.addSaveListener(e -> dataProvider.persist(e.getItem()));
    // crud.addDeleteListener(e -> dataProvider.delete(e.getItem()));
    //
    // crud.addThemeVariants(CrudVariant.NO_BORDER);

    // private CrudEditor<Person> createPersonEditor() {



    // }



    // Survey survey = surveyRepository.getSurveyBySurveyId(1l);
    //
    // add(new H1(survey.getSurveyTitle() + " " + survey.getCreator() + " " + survey.getDate()));
    //
    // survey.getQuestionList();
    //
    // for (Question q : survey.getQuestionList()) {
    // add(new H2(q.getQuestionTitle()));
    // if (q.getAlternativeList() != null) {
    // for (MultiQuestionAlternative mqa : q.getAlternativeList()) {
    // add(new H3(mqa.getAlternativeTitle()));
    // }
    // }
    // }

  }

}
