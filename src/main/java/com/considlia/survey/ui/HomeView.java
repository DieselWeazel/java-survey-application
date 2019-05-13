package com.considlia.survey.ui;

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

  private Grid<Survey> grid;
  private List<Survey> surveyList;
  private TextField idField, titleField, creatorField, dateField;
  private SurveyRepository surveyRepository;

  public HomeView(SurveyRepository surveyRepository) {
    super("Home");
    this.surveyRepository = surveyRepository;

    surveyList = new ArrayList<>();

    // Gets the list from the Database
    surveyList = surveyRepository.findAll();

    grid = new Grid<>();
    Grid.Column<Survey> idColumn = grid.addColumn(Survey::getId).setHeader("Id").setWidth("3%");
    Grid.Column<Survey> titleColumn =
        grid.addColumn(Survey::getTitle).setHeader("Title").setFlexGrow(4);

    // Shows Description from Survey
    grid.addComponentColumn(item -> showDescription(item)).setWidth("1%");
    grid.setItemDetailsRenderer(TemplateRenderer.<Survey>of(
        "<div style='border: 1px solid gray; padding: 10px; width: 100%;box-sizing: border-box;'>"
            + "<div> <b>[[item.description]]</b></div>" + "</div>")
        .withProperty("description", Survey::getDescription)
        .withEventHandler("handleClick", survey -> {
          grid.getDataProvider().refreshItem(survey);
        }));

    Grid.Column<Survey> creatorColumn = grid.addColumn(Survey::getCreator).setHeader("Creator");
    Grid.Column<Survey> dateColumn = grid.addColumn(Survey::getDate).setHeader("Date");

    grid.addComponentColumn(item -> showButtons(grid, item));
    grid.setDetailsVisibleOnClick(false);
    grid.setSelectionMode(Grid.SelectionMode.NONE);
    grid.setItems(surveyList);

    add(grid);

    HeaderRow filterRow = grid.appendHeaderRow();

    // ID filter
    idField = createFilterField(filterRow, idColumn);
    idField.addValueChangeListener(e -> idSearcher(idField.getValue()));
    idField.setMaxWidth("30%");

    // Title filter
    titleField = createFilterField(filterRow, titleColumn);
    titleField.addValueChangeListener(e -> titleSearcher(titleField.getValue()));

    // Creator filter
    creatorField = createFilterField(filterRow, creatorColumn);
    creatorField.addValueChangeListener(e -> creatorSearcher(creatorField.getValue()));

    // Date filter
    dateField = createFilterField(filterRow, dateColumn);
    dateField.addValueChangeListener(e -> dateSearcher(dateField.getValue()));

  }

  private TextField createFilterField(HeaderRow filterRow, Grid.Column<Survey> column) {
    TextField field = new TextField();
    field.setValueChangeMode(ValueChangeMode.EAGER);
    filterRow.getCell(column).setComponent(field);
    field.setPlaceholder("Filter");
    return field;
  }

  private void idSearcher(String fromFilter) {
    if (!fromFilter.equalsIgnoreCase(null) && !fromFilter.equalsIgnoreCase("")) {
      titleField.clear();
      creatorField.clear();
      dateField.clear();
      List<Survey> filteredList = new ArrayList<Survey>();
      for (Survey s : surveyList) {
        if (Long.parseLong(fromFilter) == s.getId()) {
          filteredList.add(s);
        }
        grid.setItems(filteredList);
      }
    } else {
      grid.setItems(surveyList);
    }
  }

  private void titleSearcher(String fromFilter) {
    if (!fromFilter.equalsIgnoreCase(null) && !fromFilter.equalsIgnoreCase("")) {
      idField.clear();
      creatorField.clear();
      dateField.clear();
      List<Survey> filteredList = new ArrayList<Survey>();
      for (Survey s : surveyList) {
        if (s.getTitle().toUpperCase().contains(fromFilter.toUpperCase())) {
          filteredList.add(s);
        }
        grid.setItems(filteredList);
      }
    } else {
      grid.setItems(surveyList);
    }
  }

  private void creatorSearcher(String fromFilter) {
    if (!fromFilter.equalsIgnoreCase(null) && !fromFilter.equalsIgnoreCase("")) {
      idField.clear();
      titleField.clear();
      dateField.clear();
      List<Survey> filteredList = new ArrayList<Survey>();
      for (Survey s : surveyList) {
        if (s.getCreator().toUpperCase().contains(fromFilter.toUpperCase())) {
          filteredList.add(s);
        }
        grid.setItems(filteredList);
      }
    } else {
      grid.setItems(surveyList);
    }
  }

  private void dateSearcher(String fromFilter) {
    if (!fromFilter.equalsIgnoreCase(null) && !fromFilter.equalsIgnoreCase("")) {
      idField.clear();
      titleField.clear();
      creatorField.clear();
      List<Survey> filteredList = new ArrayList<Survey>();
      for (Survey s : surveyList) {
        String datePattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
        String dateString = dateFormatter.format(s.getDate());
        if (dateString.contains(fromFilter)) {
          filteredList.add(s);
        }
        grid.setItems(filteredList);
      }
    } else {
      grid.setItems(surveyList);
    }
  }

  public HorizontalLayout showDescription(Survey item) {
    Icon info = new Icon(VaadinIcon.INFO_CIRCLE_O);
    info.addClickListener(e -> {
      grid.setDetailsVisible(item, !grid.isDetailsVisible(item));
    });
    return new HorizontalLayout(info);
  }

  public HorizontalLayout showButtons(Grid<Survey> grid, Survey item) {
    Button showSurvey = new Button(new Icon(VaadinIcon.EYE), onShow -> {
      getUI().ifPresent(ui -> ui.navigate(ShowSurveyView.class, item.getId()));
    });
    Button editSurvey = new Button(new Icon(VaadinIcon.PENCIL), onEdit -> {
      getUI().ifPresent(ui -> ui.navigate(CreateSurveyView.class, item.getId()));
    });
    Button deleteSurvey = new Button(new Icon(VaadinIcon.TRASH), onDelete -> {
      ConfirmDialog confirmDialog = new ConfirmDialog("Confirm delete",
          "Are you sure you want to delete the item?", surveyRepository, grid, item);
    });
    return new HorizontalLayout(showSurvey, editSurvey, deleteSurvey);
  }
}
