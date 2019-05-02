package com.considlia.survey.custom_component;

import com.considlia.survey.model.Survey;
import com.considlia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ConfirmDialog extends Dialog {

  public ConfirmDialog(String headerText, String contentText, SurveyRepository surveyRepository,
      Grid<Survey> grid, Survey item) {
    setCloseOnEsc(false);
    setCloseOnOutsideClick(false);

    Button cancelBtn = new Button("Cancel", onCancel -> {
      close();
    });
    Button confirmBtn = new Button("Confirm", onConfirm -> {
      surveyRepository.delete(item);
      grid.setItems(surveyRepository.findAll());
      close();
    });

    add(new H2(headerText), new H5(contentText));
    add(new HorizontalLayout(cancelBtn, confirmBtn));
    open();
  }
}
