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

  /**
   * Private Constructor, this ConfirmDialog is never meant to be instantiated. See {@link ConfirmDialogBuilder}
   */
  private ConfirmDialog() {
  }

  /**
   * Builder Class, sets up the ConfirmDialog, with whatever method is needed.
   * @param <T> being an entity to remove, update or save.
   */
  public static class ConfirmDialogBuilder<T> extends ConfirmDialog<T> {

    public Consumer<T> consumer;
    public T entityObject;
    public ContinueNavigationAction action;
    public Runnable runnable;
    public boolean allFieldsCorrectlyFilledIn;

    /**
     * Add appropriate method, or function, to the ConfirmDialog, in any order possible.
     * @param builderFunction Method to add from this ConfirmDialogBuilder class only.
     * @return {@link ConfirmDialog}
     */
    public ConfirmDialogBuilder with(
        Consumer<ConfirmDialogBuilder> builderFunction) {
      builderFunction.accept(this);
      return this;
    }

    /**
     * Creates ConfirmDialog. Call this method on the end.
     * @return a finished/built {@link ConfirmDialog}
     */
    public ConfirmDialog<T> createConfirmDialog() {
      return this;
    }

    /**
     * Adds HeaderText to the ConfirmDialog.
     * @param s being the String to display on top.
     */
    public void addHeaderText(String s) {
      add(new H2(s));
    }

    /**
     * Adds Text to the ConfirmDialog.
     * @param s being Text to add.
     */
    public void addInformationText(String s){
      add(new Text(s));
    }

    /**
     * Adds Content Text to the ConfirmDialog
     * @param s being the String of main information text to add.
     */
    public void addContentText(String s) {
      add(new H5(s));
    }

    /**
     * Adds a container with options to Confirm the entity removal/update/save process of type, and a cancel button.
     * Important: the type, has to be the same as the ConfirmDialogs instantiation type. {@link ConfirmDialog<T>}
     * Will not work without setting the {@link Consumer}
     */
    public void addRemoveAndCancelButtonsContainer() {
      Button confirmButton = new Button("Confirm", confirm -> {
        consumer.accept(entityObject);
        close();
      });
      Button cancelButton = new Button("Cancel", cancel -> close());
      add(new HorizontalLayout(confirmButton, cancelButton));
    }

    /**
     * Adds a Confirm button to save an entity.
     * Requires a method passed for the Runnable, {@link Runnable}
     */
    public void confirmSaveEntityButton() {
      if (allFieldsCorrectlyFilledIn) {
        add(new Button("Save", confirm -> runnable.run()));
      } else {
        Button saveButton = new Button("Save");
        saveButton.setEnabled(false);
        add(saveButton);
      }
    }

    /**
     * Adds a simple Close Button. Can be altered to include whatever Text to display on the button.
     * @param s being the Buttons text.
     */
    public void addSimpleCloseButton(String s){
      add(new Button(s, cancel -> close()));
    }

    /**
     * Adds a container with options to Save the entity removal/update/save process of type, a Discard button,
     * and a cancel button.
     * Important: the type, has to be the same as the ConfirmDialogs instantiation type. {@link ConfirmDialog<T>}
     * Will not work without setting the {@link Consumer}
     * Will not work without passing the desired course of action/method to the {@link Runnable}, as in, navigate to next page
     * or whatever is needed.
     */
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

    /**
     * Adds a list of all missing fields. Needs a correctly setup {@link ErrorVerificationMessageDTO}
     * @param errorVerificationMessageDTO to pass the list of missing fields.
     */
    public void addMissingFieldsList(ErrorVerificationMessageDTO errorVerificationMessageDTO) {
      for (String s : errorVerificationMessageDTO.getErrorText()) {
        add(new H5(s));
      }
    }
  }
}
