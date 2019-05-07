package com.considlia.survey.ui;

import java.util.List;
import com.considlia.survey.model.Survey;
import com.considlia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "Kalle", layout = MainLayout.class)
public class MainView extends FormLayout implements BeforeEnterObserver {
  private static final long serialVersionUID = 1L;
  private Grid<Survey> grid;
  private List<Survey> surveyList;
  private TextField idField, titleField, creatorField, dateField;
  private SurveyRepository surveyRepository;

  private Label label;

  public MainView(final SurveyRepository surveyRepository) {
    label = new Label();
    super.add(label);

    // // surveyRepository = surveyRepository;
    //
    // surveyList = new ArrayList<>();
    //
    // // Hämtar lista från Databasen
    // surveyList = surveyRepository.findAll();
    //
    // grid = new Grid<>();
    // final Grid.Column<Survey> idColumn =
    // grid.addColumn(Survey::getSurveyId).setHeader("Id").setWidth("1px");
    // final Grid.Column<Survey> titleColumn =
    // grid.addColumn(Survey::getSurveyTitle).setHeader("Title").setFlexGrow(4);
    // final Grid.Column<Survey> creatorColumn =
    // grid.addColumn(Survey::getCreator).setHeader("Creator");
    // final Grid.Column<Survey> dateColumng = grid.addColumn(Survey::getDate).setHeader("Date");
    // grid.addComponentColumn(item -> showButtons(grid, item));
    // grid.setItems(surveyList);
    // add(grid);
    //
    // final HeaderRow filterRow = grid.appendHeaderRow();
    //
    // // ID filter
    // idField = new TextField();
    // idField.addValueChangeListener(e -> idSearcher(idField.getValue()));
    // idField.setValueChangeMode(ValueChangeMode.EAGER);
    // filterRow.getCell(idColumn).setComponent(idField);
    // idField.setPlaceholder("Filter");
    //
    // // Title filter
    // titleField = new TextField();
    // titleField.addValueChangeListener(e -> titleSearcher(titleField.getValue()));
    // titleField.setValueChangeMode(ValueChangeMode.EAGER);
    // filterRow.getCell(titleColumn).setComponent(titleField);
    // titleField.setPlaceholder("Filter");
    //
    // // Creator filter
    // creatorField = new TextField();
    // creatorField.addValueChangeListener(e -> creatorSearcher(creatorField.getValue()));
    // creatorField.setValueChangeMode(ValueChangeMode.EAGER);
    // filterRow.getCell(creatorColumn).setComponent(creatorField);
    // creatorField.setPlaceholder("Filter");
    //
    // // Date filter
    // dateField = new TextField();
    // dateField.addValueChangeListener(e -> dateSearcher(dateField.getValue()));
    // dateField.setValueChangeMode(ValueChangeMode.EAGER);
    // filterRow.getCell(dateColumng).setComponent(dateField);
    // dateField.setPlaceholder("Filter");

  }

  // private void idSearcher(final String fromFilter) {
  // if (!fromFilter.equalsIgnoreCase(null) && !fromFilter.equalsIgnoreCase("")) {
  // titleField.clear();
  // creatorField.clear();
  // dateField.clear();
  // final List<Survey> filteredList = new ArrayList<Survey>();
  // for (final Survey s : surveyList) {
  // if (Long.parseLong(fromFilter) == s.getSurveyId()) {
  // filteredList.add(s);
  // }
  // grid.setItems(filteredList);
  // }
  // } else {
  // grid.setItems(surveyList);
  // }
  // }
  //
  // private void titleSearcher(final String fromFilter) {
  // if (!fromFilter.equalsIgnoreCase(null) && !fromFilter.equalsIgnoreCase("")) {
  // idField.clear();
  // creatorField.clear();
  // dateField.clear();
  // final List<Survey> filteredList = new ArrayList<Survey>();
  // for (final Survey s : surveyList) {
  // if (s.getSurveyTitle().toUpperCase().contains(fromFilter.toUpperCase())) {
  // filteredList.add(s);
  // }
  // grid.setItems(filteredList);
  // }
  // } else {
  // grid.setItems(surveyList);
  // }
  // }
  //
  // private void creatorSearcher(final String fromFilter) {
  // if (!fromFilter.equalsIgnoreCase(null) && !fromFilter.equalsIgnoreCase("")) {
  // idField.clear();
  // titleField.clear();
  // dateField.clear();
  // final List<Survey> filteredList = new ArrayList<Survey>();
  // for (final Survey s : surveyList) {
  // if (s.getCreator().toUpperCase().contains(fromFilter.toUpperCase())) {
  // filteredList.add(s);
  // }
  // grid.setItems(filteredList);
  // }
  // } else {
  // grid.setItems(surveyList);
  // }
  // }
  //
  // private void dateSearcher(final String fromFilter) {
  // if (!fromFilter.equalsIgnoreCase(null) && !fromFilter.equalsIgnoreCase("")) {
  // idField.clear();
  // titleField.clear();
  // creatorField.clear();
  // final List<Survey> filteredList = new ArrayList<Survey>();
  // for (final Survey s : surveyList) {
  // final String datePattern = "yyyy-MM-dd";
  // final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
  // final String dateString = dateFormatter.format(s.getDate());
  // if (dateString.contains(fromFilter)) {
  // filteredList.add(s);
  // }
  // grid.setItems(filteredList);
  // }
  // } else {
  // grid.setItems(surveyList);
  // }
  // }
  //
  // public HorizontalLayout showButtons(final Grid<Survey> grid, final Survey item) {
  // final Button showSurvey = new Button(new Icon(VaadinIcon.EYE), onShow -> {
  // getUI().ifPresent(ui -> ui.navigate(ShowSurveyView.class, item.getSurveyId()));
  // });
  // final Button editSurvey = new Button(new Icon(VaadinIcon.PENCIL), onEdit -> {
  // getUI().ifPresent(ui -> ui.navigate(CreateSurveyView.class, item.getSurveyId()));
  // });
  // final Button deleteSurvey = new Button(new Icon(VaadinIcon.TRASH), onDelete -> {
  // final ConfirmDialog confirmDialog = new ConfirmDialog("Confirm delete",
  // "Are you sure you want to delete the item?", surveyRepository, grid, item);
  // });
  //
  // return new HorizontalLayout(showSurvey, editSurvey, deleteSurvey);
  // }

  @Override
  public void beforeEnter(final BeforeEnterEvent event) {
    label.setText("Fredrik");

  }
}
