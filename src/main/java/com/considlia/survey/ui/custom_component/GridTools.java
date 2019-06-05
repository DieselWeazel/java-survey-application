package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Survey;
import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.ShowSurveyView;
import com.considlia.survey.ui.custom_component.ConfirmDialog.ConfirmDialogBuilder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.util.function.Consumer;

public class GridTools extends HorizontalLayout {

  public GridTools() {
    // Empty Constructor
  }

  /**
   * Constructor for HomeView SurveyGrid.
   * @param item for each Survey to be able to be viewed.
   */
  public GridTools(Survey item) {
    add(showSurveyButton(item));
  }

  /**
   * Constructor for ProfileView.
   * @param item for each Survey.
   * @param deleteSurveyConsumer for each Survey to be able to be deleted, edited and viewed.
   */
  public GridTools(Survey item, Consumer<Survey> deleteSurveyConsumer) {
    add(showSurveyButton(item), editSurveyButton(item), deleteSurveyButton(item, deleteSurveyConsumer));
  }

  /**
   * Opens Survey
   * @param item Survey to View.
   * @return on click, navigates to {@link ShowSurveyView}
   */
  private Button showSurveyButton(Survey item) {
    return new Button(
        new Icon(VaadinIcon.EYE),
        onShow -> getUI().ifPresent(ui -> ui.navigate(ShowSurveyView.class, item.getId())));
  }

  /**
   * Creates a button to edit surveys from inside ProfileView.
   * @param item Survey to edit, to find ID/parameter.
   * @return on click, navigates to {@link CreateSurveyView} with Survey parameter.
   */
  private Button editSurveyButton(Survey item) {
    return new Button(
        new Icon(VaadinIcon.PENCIL),
        onEdit -> getUI().ifPresent(ui -> ui.navigate(CreateSurveyView.class, item.getId())));
  }

  /**
   * Creates a button to manage Survey deletion from inside ProfileView
   * @param item Survey to delete.
   * @param deleteSurveyConsumer consumer that deletes Survey if confirmed within ConfirmDialogBuilder.
   * @return confirm dialog, to make sure user wants to delete survey.
   */
  private Button deleteSurveyButton(Survey item, Consumer<Survey> deleteSurveyConsumer) {
    return new Button(
        new Icon(VaadinIcon.TRASH),
        onDelete -> {

          ConfirmDialog<Survey> confirmDialog = new ConfirmDialogBuilder<Survey>()
              .with($ -> {
                $.consumer = deleteSurveyConsumer;
                $.entityObject = item;
                $.addHeaderText("Confirm Delete");
                $.addContentText("Are you sure you want to delete " + item.getTitle() + "?");
              })
              .createConfirmDialog();
          confirmDialog.open();
        });
  }
}
