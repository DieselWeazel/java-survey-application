package com.considlia.survey.ui.custom_component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeLeaveEvent.ContinueNavigationAction;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A Universal ConfirmDialog class, with purpose to be applicable to all kinds of Confirm Dialogs that are needed.
 * @param <T> being an entity to remove, update or save.
 */
public class ConfirmDialog<T> extends Dialog {

  public Consumer<T> consumer;
  public T entityObject;
  public H2 headerText;
  public Text text;
  public String textString;
  public String headerString;
  public H5 contentText;
  public String contentString;
  private String cancelString;
  public ContinueNavigationAction action;
  public Runnable runnable;
  public boolean allFieldsCorrectlyFilledIn;
  public ErrorVerificationMessageDTO errorVerificationMessageDTO;
  public List<String> errorQuestionList;

  public ConfirmDialog() {
  }

  public static class ConfirmDialogBuilder<T> extends ConfirmDialog<T> {

    public Consumer<T> consumer;
    public T entityObject;
    public H2 headerText;
    public Text text;
    public String textString;
    public String headerString;
    public H5 contentText;
    public String contentString;
    private String cancelString;
    public ContinueNavigationAction action;
    public Runnable runnable;
    public boolean allFieldsCorrectlyFilledIn;
    public ErrorVerificationMessageDTO errorVerificationMessageDTO;
    public List<String> errorQuestionList;

    public ConfirmDialogBuilder with(
        Consumer<ConfirmDialogBuilder> builderFunction) {
      builderFunction.accept(this);
      return this;
    }

    public ConfirmDialog<T> createConfirmDialog() {
      return this;
    }

    public void addHeaderText(String s) {
      add(new H2(s));
    }

    public void addInformationText(String s){
      add(new Text(s));
    }

    public void addContentText(String s) {
      add(new H5(s));
    }

    public void confirmInformationButton(){
      add(new Button("Ok", ok -> close()));
    }

    public void addRemoveAndCancelButtonsContainer() {
      Button confirmButton = new Button("Confirm", confirm -> {
        consumer.accept(entityObject);
        close();
      });
      Button cancelButton = new Button("Cancel", cancel -> close());
      add(new HorizontalLayout(confirmButton, cancelButton));
    }

    public void confirmSaveEntityButton() {
      if (allFieldsCorrectlyFilledIn) {
        add(new Button("Save", confirm -> runnable.run()));
      } else {
        Button saveButton = new Button("Save");
        saveButton.setEnabled(false);
        add(saveButton);
      }
    }

    public void addSimpleCloseButton(String s){
      add(new Button(s, cancel -> close()));
    }

    public void addSaveDiscardCancelAlternatives(){
      Button saveButton = new Button("Save", confirm -> {
        runnable.run();
        action.proceed();
        close();
      });
      if (!allFieldsCorrectlyFilledIn){
        saveButton.setEnabled(false);
      }
      Button discardButton = new Button("Discard", discard -> {
        action.proceed();
        close();
      });
      Button closeButton = new Button("Close", close -> close());
      add(new HorizontalLayout(saveButton, discardButton, closeButton));
    }

    public void addMissingFieldsList(ErrorVerificationMessageDTO errorVerificationMessageDTO) {
      for (String s : errorVerificationMessageDTO.getErrorText()) {
        add(new H5(s));
      }
    }
  }
}
