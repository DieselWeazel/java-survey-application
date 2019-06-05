package com.considlia.survey.ui.custom_component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeLeaveEvent.ContinueNavigationAction;
import java.util.function.Consumer;

public class ConfirmDialog<T> extends Dialog {

  private Consumer<T> consumer;
  private T entityObject;
  private H2 headerText;
  private H5 contentText;
  private ContinueNavigationAction action;
  private Runnable runnable;
  private boolean allFieldsCorrectlyFilledIn;
  private Button confirmEntityRemovalButton;
  private Button confirmSaveEntityButton;
  private Button cancelButton;
  private Button discardButton;
  private HorizontalLayout saveDiscardCancelButtonContainer;

  private ConfirmDialog(Consumer<T> consumer, T entityObject, H2 headerText, Text infoText, H5 contentText, ContinueNavigationAction action,
      Runnable runnable, boolean allFieldsCorrectlyFilledIn, Button confirmEntityRemovalButton, Button confirmSaveEntityButton,
      Button cancelButton, Button discardButton, HorizontalLayout saveDiscardCancelButtonContainer) {

    add(headerText, contentText);
    add(infoText);
    add(confirmEntityRemovalButton);
    add(cancelButton);
    add(saveDiscardCancelButtonContainer);
    open();
  }

  public static class ConfirmDialogBuilder<T> extends Dialog {

    public Consumer<T> consumer;
    public T entityObject;
    public H2 headerText;
    public Text text;
    public String textString;
    public String headerString;
    public H5 contentText;
    public String contentString;
    public ContinueNavigationAction action;
    public Runnable runnable;
    public boolean allFieldsCorrectlyFilledIn;

//    public Button confirmEntityRemovalButton;
//    public Button cancelButton;

    //  public static Builder

    public ConfirmDialogBuilder with(
        Consumer<ConfirmDialogBuilder> builderFunction) {
      builderFunction.accept(this);
      return this;
    }

    public ConfirmDialog<T> createConfirmDialog() {
      return new ConfirmDialog<T>(
          consumer,
          entityObject,
          headerText(headerString),
          informationText(textString),
          contentText(contentString),
          action,
          runnable,
          allFieldsCorrectlyFilledIn,
          confirmEntityRemovalButton(),
          confirmSaveEntityButton(),
          cancelButton(),
          discardButton(),
          saveDiscardCancelButtonContainer());
    }

    public H2 headerText(String headerString) {
      return new H2(headerString);
    }

    public Text informationText(String textString){
      return new Text(textString);
    }

    public H5 contentText(String contentString) {
      return new H5(contentString);
    }

    public Button confirmEntityRemovalButton() {
      return new Button("Confirm", confirm -> consumer.accept(entityObject));
    }

    public Button confirmSaveEntityButton() {
      if (allFieldsCorrectlyFilledIn) {
        return new Button("Save", confirm -> runnable.run());
      } else {
        Button saveButton = new Button("Save");
        saveButton.setEnabled(false);
        return saveButton;
      }
    }

    public Button cancelButton() {
      return new Button("Cancel", cancel -> close());
    }

    public Button discardButton() {
      return new Button("Discard", discard -> action.proceed());
    }

    public HorizontalLayout saveDiscardCancelButtonContainer(){
      return new HorizontalLayout(confirmSaveEntityButton(), discardButton(), cancelButton());
    }
  }
}
