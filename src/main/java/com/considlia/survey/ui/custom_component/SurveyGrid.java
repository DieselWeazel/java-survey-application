package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Survey;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.security.CustomUserService;
import com.considlia.survey.ui.HomeView;
import com.vaadin.flow.component.grid.Grid;
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
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

/*
isHome shows if Home or not, always add according to if (isHome) or not.
Jonathan
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

  private SurveyRepository surveyRepository;

  @Autowired
  private CustomUserService customUserService;

  @Autowired
  private UserRepository userRepository;
  private boolean isHome = false;

  public SurveyGrid(
      Class viewingLayout, SurveyRepository surveyRepository) {
    isHome = HomeView.class.equals(viewingLayout);
    this.surveyRepository = surveyRepository;
    this.surveyList = new ArrayList<>();
    init();
  }

  public SurveyGrid(
      Class viewingLayout, SurveyRepository surveyRepository, CustomUserService customUserService) {
    isHome = HomeView.class.equals(viewingLayout);
    this.surveyRepository = surveyRepository;
    this.customUserService = customUserService;
    this.surveyList = new ArrayList<>();
    init();
  }

  @PostConstruct
  public void init() {
    grid = new Grid<>();
    surveyList = surveyRepository.findAll();
    if (!isHome) {
      surveyList = surveyRepository.findAllByUserId(customUserService.getUser().getId());
    }
    generateGridColumns();
    grid.setItems(surveyList);
  }

  /**
   * Generates Grid Columns, in case of Profile View we don't add Creator Column, instead add more
   * tools to our Grid.
   */
  private void generateGridColumns() {
    idColumn = grid.addColumn(Survey::getId).setHeader("Id").setWidth("3%");
    titleColumn = grid.addColumn(Survey::getTitle).setHeader("Title").setFlexGrow(4);

    // Shows Description from Survey
    grid.addComponentColumn(item -> showDescription(item)).setWidth("1%");
    grid.setItemDetailsRenderer(
        TemplateRenderer.<Survey>of(
            "<div style='border: 1px solid gray; padding: 10px; width: 100%;box-sizing: border-box;'>"
                + "<div> <b>[[item.description]]</b></div>"
                + "</div>")
            .withProperty("description", Survey::getDescription)
            .withEventHandler(
                "handleClick",
                survey -> {
                  grid.getDataProvider().refreshItem(survey);
                }));

    if (isHome) {
      creatorColumn = grid.addColumn(Survey::getCreator).setHeader("Creator");
    }
    dateColumn = grid.addColumn(Survey::getDate).setHeader("Date");

    // Includes deleteSurvey method to Consumer if we are on ProfileView.
    if (!isHome) {
      grid.addComponentColumn(item -> {
        GridTools gridTools = new GridTools(item, this::updateGridData);
        return gridTools;
      });
    } else {
      grid.addComponentColumn(item -> {
        GridTools gridTools = new GridTools(item);
        return gridTools;
      });
    }
    grid.setDetailsVisibleOnClick(false);
    grid.setSelectionMode(Grid.SelectionMode.NONE);

    add(grid);
    filterRow = grid.appendHeaderRow();
    createFilterFields();
  }

  private void createFilterFields() {
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
    List<Survey> filteredList = new ArrayList<>(surveyList);
    if (!"".equals(filterValue)) {
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
    info.addClickListener(e -> grid.setDetailsVisible(item, !grid.isDetailsVisible(item)));
    return new HorizontalLayout(info);
  }

  private void updateGridData(Survey survey) {
    surveyRepository.delete(survey);
    grid.setItems(
        surveyList = surveyRepository.findAllByUserId(customUserService.getUser().getId()));
  }
}