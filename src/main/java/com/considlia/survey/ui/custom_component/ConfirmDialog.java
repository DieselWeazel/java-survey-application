package com.considlia.survey.ui.custom_component;

import java.util.function.Consumer;
import com.considlia.survey.model.Survey;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.custom_component.question_with_button.QuestionWithButtons;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeLeaveEvent.ContinueNavigationAction;

public class ConfirmDialog extends Dialog {

  private Button cancelBtn;

  /**
   * Dialog that confirms if you want to delete the passes {@link Survey}
   * 
   * @param headerText - {@link String} text in header
   * @param contentText - {@link String} text in body
   * @param consumer - method from {@link SurveyGrid} that removes the item-parameter
   * @param item - {@link Survey} thats going to be removed
   */
  public ConfirmDialog(String headerText, String contentText, Consumer<Survey> consumer,
      Survey item) {
    setCloseOnEsc(false);
    setCloseOnOutsideClick(false);

    initCancelBtn();

    Button confirmBtn = new Button("Confirm", onConfirm -> {
      consumer.accept(item);
      close();
    });

    add(new H2(headerText), new H5(contentText));
    add(new HorizontalLayout(cancelBtn, confirmBtn));
    open();
  }

  /**
   * Discards, cancels or saves the changes made to a {@link Survey}
   * 
   * @param action - {@link ContinueNavigationAction}
   * @param survey - {@link CreateSurveyView}
   */
  public ConfirmDialog(ContinueNavigationAction action, CreateSurveyView survey) {

    initCancelBtn();

    Button confirmBtn = new Button("Discard", onDiscard -> {
      action.proceed();
      close();
    });
    Button saveBtn = new Button("Save", onSave -> {
      survey.saveSurvey();
      action.proceed();
      close();
    });

    HorizontalLayout buttonContainer = new HorizontalLayout();
    if (!survey.checkFilledFields()) {
      saveBtn.setEnabled(false);
      add(new Text(
          "You have to fill out required fields and have at least one question. Fill them out or discard changes"));
    } else {
      add(new H5("Do you want to save or discard your changes before navigating away?"));
    }

    buttonContainer.add(saveBtn, confirmBtn, cancelBtn);
    add(buttonContainer);
  }

  /**
   * Creates a {@link Dialog} where the "Confirm"-{@link Button} removes the {@link Question} from
   * the survey.
   * 
   * @param survey - {@link CreateSurveyView} to get access to its methods
   * @param question - the {@link QuestionWithButtons} thats going to be removed
   */
  public ConfirmDialog(CreateSurveyView survey, QuestionWithButtons question) {

    initCancelBtn();

    Button confirmBtn = new Button("Confirm", onConfirm -> {
      survey.removeQuestion(question);
      close();
    });
    add(new H5("Are you sure you want to remove this question?"));
    add(new HorizontalLayout(cancelBtn, confirmBtn));
    open();
  }

  /**
   * Dialog Windows connected to Login/Registration
   */
  /**
   * Dialog showing a error message, if User Username exists.
   */
  public ConfirmDialog(){
    add(new H5("Wrong Username or Password, try again!"));
    add(new Button("Ok", e -> close()));
  }
  /**
   * Dialog showing a error message, if User Username exists.
   */
  public ConfirmDialog(String userinput) {
    add(new H5("Error, username: " + userinput + " is already taken, please take another one."));
    add(new Button("Ok", e -> close()));
  }

  /**
   * Dialog showing a error message, if User Email exists.
   */
  public ConfirmDialog(String userinput, boolean email){
    add(new H5("There already exists a User registered with this email, have you forgotten your password?"));
    add(new Button("Ok", e -> close()));
  }

  /**
   * Creates a {@link Button} with the text "Cancel" and a {@link ClickEvent} that closes the
   * {@link Dialog}
   */
  public void initCancelBtn() {
    cancelBtn = new Button("Cancel", onCancel -> {
      close();
    });
  }
}
