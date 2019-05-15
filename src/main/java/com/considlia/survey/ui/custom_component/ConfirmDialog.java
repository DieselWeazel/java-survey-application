package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Survey;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.custom_component.question_with_button.QuestionWithButtons;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeLeaveEvent.ContinueNavigationAction;
import java.util.List;

public class ConfirmDialog extends Dialog {

  private Button cancelBtn;

  public ConfirmDialog(
      String headerText,
      String contentText,
      SurveyRepository surveyRepository,
      List<Survey> surveyList,
      Grid<Survey> grid,
      Survey item) {
    setCloseOnEsc(false);
    setCloseOnOutsideClick(false);

    initCancelBtn();

    Button confirmBtn =
        new Button(
            "Confirm",
            onConfirm -> {
              System.out.println(item.toString());
              surveyRepository.delete(item);
              grid.setItems(surveyList);
              close();
            });

    add(new H2(headerText), new H5(contentText));
    add(new HorizontalLayout(cancelBtn, confirmBtn));
    open();
  }

  public ConfirmDialog(ContinueNavigationAction action, CreateSurveyView survey) {

    initCancelBtn();

    Button confirmBtn =
        new Button(
            "Discard",
            onDiscard -> {
              action.proceed();
              close();
            });
    Button saveBtn =
        new Button(
            "Save",
            onSave -> {
              survey.saveSurvey();
              action.proceed();
              close();
            });

    HorizontalLayout buttonContainer = new HorizontalLayout();
    if (!survey.checkFilledFields()) {
      saveBtn.setEnabled(false);
      add(
          new Text(
              "You have to fill out required fields and have at least one question. Fill them out or discard changes"));
    } else {
      add(new H5("Do you want to save or discard your changes before navigating away?"));
    }

    buttonContainer.add(saveBtn, confirmBtn, cancelBtn);
    add(buttonContainer);
  }

  public ConfirmDialog(CreateSurveyView survey, QuestionWithButtons question) {

    initCancelBtn();

    Button confirmBtn =
        new Button(
            "Confirm",
            onConfirm -> {
              survey.removeQuestion(question);
              close();
            });
    add(new H5("Are you sure you want to remove this question?"));
    add(new HorizontalLayout(cancelBtn, confirmBtn));
    open();
  }

  public void initCancelBtn() {
    cancelBtn =
        new Button(
            "Cancel",
            onCancel -> {
              close();
            });
  }
}
