package com.considlia.survey.ui.custom_component;

import java.util.function.Consumer;
import com.considlia.survey.model.Survey;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeLeaveEvent.ContinueNavigationAction;

/**
 * Class that handles all types of ConfirmDialogs, applicable to several forms of Confirms.
 * @param <T>
 */
public class ConfirmDialog<T> extends Dialog {

  private Button cancelBtn;

  /**
   * Dialog that confirms if you want to delete the passed T, entityObject
   * @param headerText - {@link String} text in header
   * @param contentText - {@link String} text in body
   * @param consumer - method from {@link SurveyGrid} that removes the item-parameter
   * @param entityObject being the Entity to remove, or handle.
   */
  public ConfirmDialog(String headerText, String contentText, Consumer<T> consumer,
      T entityObject) {
    setCloseOnEsc(false);
    setCloseOnOutsideClick(false);

    initCancelBtn();

    Button confirmBtn = new Button("Confirm", onConfirm -> {
      consumer.accept(entityObject);
      close();
    });

    add(new H2(headerText), new H5(contentText));
    add(new HorizontalLayout(cancelBtn, confirmBtn));
    open();
  }

  /**
   * Discards, cancels or saves the changes made to a {@link Survey} or {@link com.considlia.survey.model.SurveyResponse},
   * can be used with other entity creation views.
   *
   * @param action - {@link ContinueNavigationAction}
   * @param runnable - Method saving entity/object.
   * @param checkFilledFields boolean, if some fields are unfinished during creation process.
   */
  public ConfirmDialog(ContinueNavigationAction action, Runnable runnable, boolean checkFilledFields) {

    initCancelBtn();

    Button confirmBtn = new Button("Discard", onDiscard -> {
      action.proceed();
      close();
    });
    Button saveBtn = new Button("Save", onSave -> {
      runnable.run();
      action.proceed();
      close();
    });

    HorizontalLayout buttonContainer = new HorizontalLayout();
    if (!checkFilledFields) {
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
   * ConfirmDialog to handle DTO Verification, this Confirm Dialog is applicable to
   * all usecases, since input parameter is of class {@link ErrorVerificationMessageDTO}
   * If only one String, only pass one String into the array.
   *
   * Can be expanded/overloaded to handle constructors of type
   * {@link ErrorVerificationMessageDTO} with only one String.
   * @param errorVerificationMessageDTO
   * Written by Jonathan Harr
   */
  public ConfirmDialog(ErrorVerificationMessageDTO errorVerificationMessageDTO){
    for (String s : errorVerificationMessageDTO.getErrorText()){
      add(new H5(s));
    }
    add(new Button("Understood!", e-> close()));
  }

  /**
   * Dialog showing a error message, message being input to explain what went wrong.
   */
  public ConfirmDialog(String confirmDialogMessage) {
    add(new H5(confirmDialogMessage));
    add(okButton());
  }

  /**
   * Constructs a new {@link Button} with the text set to "Ok". {@link ClickEvent} is set to
   * {@link Dialog#close()}. The constructed button is focused.
   * 
   * @return button - {@link Button}
   */
  public Button okButton() {
    return new Button("Ok", e-> close());
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
