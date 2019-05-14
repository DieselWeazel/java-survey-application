package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Survey;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.HomeView;
import com.considlia.survey.ui.ShowSurveyView;
import com.considlia.survey.ui.UserViews.MyProfileView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import org.springframework.beans.factory.annotation.Autowired;
/*
Currently reads both MyProfileView.class and HomeView.class as being at home, change this by deleting myProfileView.class
Jonathan

!isHome at bottom should be isHome
 */
public class SurveyGrid extends VerticalLayout {

  private Grid<Survey> grid;
  private Grid.Column<Survey> idColumn;
  private Grid.Column<Survey> titleColumn;
  private Grid.Column<Survey> creatorColumn;
  private Grid.Column<Survey> dateColumn;
  private HeaderRow filterRow;

  private List<Survey> surveyList;
  private TextField idField, titleField, creatorField, dateField;

//  @Autowired
  private SurveyRepository surveyRepository;

  public SurveyGrid(Class viewingLayout, SurveyRepository surveyRepository){
    final boolean isHome = HomeView.class.equals(viewingLayout);
    grid = new Grid<>();
    this.surveyRepository = surveyRepository;
    surveyList = new ArrayList<>();
    surveyList = surveyRepository.findAll();

    idColumn = grid.addColumn(Survey::getId).setHeader("Id").setWidth("3%");
    titleColumn = grid.addColumn(Survey::getTitle).setHeader("Title").setFlexGrow(4);

    // Shows Description from Survey
    grid.addComponentColumn(item -> showDescription(item)).setWidth("1%");
    grid.setItemDetailsRenderer(TemplateRenderer.<Survey>of(
        "<div style='border: 1px solid gray; padding: 10px; width: 100%;box-sizing: border-box;'>"
            + "<div> <b>[[item.description]]</b></div>" + "</div>")
        .withProperty("description", Survey::getDescription)
        .withEventHandler("handleClick", survey -> {
          grid.getDataProvider().refreshItem(survey);
        }));
    creatorColumn = grid.addColumn(Survey::getCreator).setHeader("Creator");
    dateColumn = grid.addColumn(Survey::getDate).setHeader("Date");


    // Init Buttons, adds correct
    grid.addComponentColumn(item -> showButtons(grid, item, isHome));
    grid.setDetailsVisibleOnClick(false);
    grid.setSelectionMode(Grid.SelectionMode.NONE);
    grid.setItems(surveyList);

    add(grid);
    filterRow = grid.appendHeaderRow();

    //InitGrid Method, sets Grid according to correct view
    initGrid(isHome);
  }

  private void initGrid(boolean isHome) {
    if (isHome) {
      initHomeGrid(isHome);
    } else {
      initProfileGrid(isHome);
    }
  }

  private void initProfileGrid(boolean isHome) {
    grid.removeAllColumns();
    idColumn = grid.addColumn(Survey::getId).setHeader("Id").setWidth("3%");
    titleColumn = grid.addColumn(Survey::getTitle).setHeader("Title").setFlexGrow(4);
    dateColumn = grid.addColumn(Survey::getDate).setHeader("Date");
    grid.addComponentColumn(item -> showButtons(grid, item, isHome));
    createFilterFields(isHome);
  }


  private void initHomeGrid(boolean isHome) {
    createFilterFields(isHome);
  }

  private void createFilterFields(boolean isHome) {
    // ID filter
    idField = createFilterField(filterRow, idColumn);
    idField.addValueChangeListener(
        e ->
            filter(
                idField.getValue(),
                (survey, filterValue) -> {
                  return !survey.getId().equals(Long.parseLong(filterValue));
                })); // Title filter

    titleField = createFilterField(filterRow, titleColumn);
    titleField.addValueChangeListener(
        e ->
            filter(
                titleField.getValue(),
                (survey, filterValue) -> {
                  return !survey.getTitle().toUpperCase().contains(filterValue.toUpperCase());
                }));
    // Date filter
    dateField = createFilterField(filterRow, dateColumn);
    dateField.addValueChangeListener(
        e ->
            filter(
                dateField.getValue(),
                (survey, filterValue) -> {
                  return !survey
                      .getDate()
                      .format(DateTimeFormatter.ISO_LOCAL_DATE)
                      .contains(filterValue);
                }));
    if (isHome) {
      // Creator filter
      creatorField = createFilterField(filterRow, creatorColumn);
      creatorField.addValueChangeListener(
          e ->
              filter(
                  creatorField.getValue(),
                  (survey, filterValue) -> {
                    return !survey.getCreator().toUpperCase().contains(filterValue.toUpperCase());
                  }));
      }
  }

  private TextField createFilterField(HeaderRow filterRow, Grid.Column<Survey> column) {
    TextField filterField = new TextField();
    filterField.setValueChangeMode(ValueChangeMode.EAGER);
    filterRow.getCell(column).setComponent(filterField);
    filterField.setPlaceholder("Filter");
    return filterField;
  }

  private void filter(String filterValue, BiFunction<Survey, String, Boolean> func) {
    List<Survey> filteredList = new ArrayList<>(surveyList);    if (!"".equals(filterValue)) {
      for (Iterator<Survey> iter = filteredList.iterator(); iter.hasNext(); ) {
        if (func.apply(iter.next(), filterValue)) {
          iter.remove();
        }
      }
    }
    grid.setItems(filteredList);
  }

  private HorizontalLayout showDescription(Survey item) {
    Icon info = new Icon(VaadinIcon.INFO_CIRCLE_O);
    info.addClickListener(e -> {
      grid.setDetailsVisible(item, !grid.isDetailsVisible(item));
    });
    return new HorizontalLayout(info);
  }

  private Component showButtons(Grid<Survey> grid, Survey item, boolean isHome) {
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
    if (isHome){
      return new HorizontalLayout(showSurvey);
    } else {
      return new HorizontalLayout(showSurvey, editSurvey, deleteSurvey);
    }
  }
}



