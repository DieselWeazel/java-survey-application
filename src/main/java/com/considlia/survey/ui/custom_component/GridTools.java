package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Survey;
import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.ShowSurveyView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.util.function.Consumer;

public class GridTools extends HorizontalLayout {

  public GridTools() {
    // Empty Constructor
  }

  public GridTools(Survey item) {
    add(showSurvey(item));
  }

  public GridTools(Survey item, Consumer<Survey> consumer) {
    add(showSurvey(item), editSurvey(item), deleteSurvey(item, consumer));
  }

  private Button showSurvey(Survey item) {
    return new Button(
        new Icon(VaadinIcon.EYE),
        onShow -> getUI().ifPresent(ui -> ui.navigate(ShowSurveyView.class, item.getId())));
  }

  private Button editSurvey(Survey item) {
    return new Button(
        new Icon(VaadinIcon.PENCIL),
        onEdit -> getUI().ifPresent(ui -> ui.navigate(CreateSurveyView.class, item.getId())));
  }

  private Button deleteSurvey(Survey item, Consumer<Survey> consumer) {
    return new Button(
        new Icon(VaadinIcon.TRASH),
        onDelete -> {
          ConfirmDialog confirmDialog =
              new ConfirmDialog(
                  "Confirm Delete", "Are you sure you want to delete the item?", consumer, item);
        });
  }
}
