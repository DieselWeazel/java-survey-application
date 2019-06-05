//package com.considlia.survey.ui.custom_component;
//
//import java.util.function.Consumer;
//import com.considlia.survey.model.Survey;
//import com.vaadin.flow.component.Text;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.dialog.Dialog;
//import com.vaadin.flow.component.html.H2;
//import com.vaadin.flow.component.html.H5;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.router.BeforeLeaveEvent.ContinueNavigationAction;
//
///**
// * Class that handles all types of ConfirmDialogs, applicable to several forms of Confirms.
// * @param <T>
// */
//
//
//public class ConfirmDialogBuilder<T> extends Dialog {
//
//  public Consumer<T> consumer;
//  public T entityObject;
//  public String addHeaderText;
//  public String addContentText;
//  public ContinueNavigationAction action;
//  public Runnable runnable;
//  public boolean allFieldsCorrectlyFilledIn;
//  public Button confirmButton;
//  public Button cancelButton;
//
//
////  public static Builder
//
//  public ConfirmDialogBuilder with (Consumer<ConfirmDialogBuilder> builderFucntion){
//    builderFucntion.accept(this);
//    return this;
//  }
//
//
//  public Consumer<T> consumer(){
//    return consumer;
//  }
//
//  public Button confirmButton(){
//    return new Button("Confirm", confirm -> consumer.accept(entityObject));
//  }
//
//
//  public Button cancelButton(){
//    return new Button("Cancel", cancel -> close());
//  }
//
//  public String addHeaderText(String s){
//    return s;
//  }
//
//  /**
//   * Dialog that confirms if you want to delete the passed T, entityObject
//   * @param addHeaderText - {@link String} text in header
//   * @param addContentText - {@link String} text in body
//   * @param consumer - method from {@link SurveyGrid} that removes the item-parameter
//   * @param entityObject being the Entity to remove, or handle.
//   */
//  public ConfirmDialogBuilder(String addHeaderText, String addContentText, Consumer<T> consumer,
//      T entityObject) {
//    setCloseOnEsc(false);
//    setCloseOnOutsideClick(false);
//
//    cancelButton();
//
//    Button confirmBtn = new Button("Confirm", onConfirm -> {
//      consumer.accept(entityObject);
//      close();
//    });
//
//    add(new H2(addHeaderText), new H5(addContentText));
//    add(new HorizontalLayout(cancelButton(), confirmBtn));
//    open();
//  }
//
//  /**
//   * Discards, cancels or saves the changes made to a {@link Survey} or {@link com.considlia.survey.model.SurveyResponse},
//   * can be used with other entity creation views.
//   *
//   * @param action - {@link ContinueNavigationAction}
//   * @param runnable - Method saving entity/object.
//   * @param checkFilledFields boolean, if some fields are unfinished during creation process.
//   */
//  public ConfirmDialogBuilder(ContinueNavigationAction action, Runnable runnable, boolean checkFilledFields) {
//
//    Button confirmBtn = new Button("Discard", onDiscard -> {
//      action.proceed();
//      close();
//    });
//    Button saveBtn = new Button("Save", onSave -> {
//      runnable.run();
//      action.proceed();
//      close();
//    });
//
//    HorizontalLayout buttonContainer = new HorizontalLayout();
//    if (!checkFilledFields) {
//      saveBtn.setEnabled(false);
//      add(new Text(
//          "You have to fill out required fields and have at least one question. Fill them out or discard changes"));
//    } else {
//      add(new H5("Do you want to save or discard your changes before navigating away?"));
//    }
//
//    buttonContainer.add(saveBtn, confirmBtn, cancelButton());
//    add(buttonContainer);
//  }
//
//  /**
//   * ConfirmDialogBuilder to handle DTO Verification, this Confirm Dialog is applicable to
//   * all usecases, since input parameter is of class {@link ErrorVerificationMessageDTO}
//   * If only one String, only pass one String into the array.
//   *
//   * Can be expanded/overloaded to handle constructors of type
//   * {@link ErrorVerificationMessageDTO} with only one String.
//   * @param errorVerificationMessageDTO
//   * Written by Jonathan Harr
//   */
//  public ConfirmDialogBuilder(ErrorVerificationMessageDTO errorVerificationMessageDTO){
//    for (String s : errorVerificationMessageDTO.getErrorText()){
//      add(new H5(s));
//    }
//    add(new Button("Understood!", e-> close()));
//  }
//
//  /**
//   * Dialog showing a error message, message being input to explain what went wrong.
//   */
//  public ConfirmDialogBuilder(String confirmDialogMessage) {
//    add(new H5(confirmDialogMessage));
//    add(okButton());
//  }
//
////  /**
////   * Constructs a new {@link Button} with the text set to "Ok". {@link ClickEvent} is set to
////   * {@link Dialog#close()}. The constructed button is focused.
////   *
////   * @return button - {@link Button}
////   */
////  public Button confirmEntityRemovalButton() {
////    return new Button("Ok", e-> close());
////  }
////
////  /**
////   * Creates a {@link Button} with the text "Cancel" and a {@link ClickEvent} that closes the
////   * {@link Dialog}
////   */
////  public Button cancelButton() {
////    return new Button("Cancel", onCancel -> {
////      close();
////    });
////  }
////
////  public static class Builder {
////
////    private consumer<T> consumer;
////    private T entityObject;
////    private String addHeaderText;
////    private String addContentText;
////    private ContinueNavigationAction action;
////    private Runnable runnable;
////    private boolean allFieldsCorrectlyFilledIn;
////    private Button confirmEntityRemovalButton;
////    private Button cancelButton;
////
////
////    private Builder() {
////    }
////  }
////}
////  private ConfirmDialogBuilder(Builder builder){
////    this.consumer = builder.consumer;
////    this.entityObject = builder.entityObject;
////    this.addHeaderText = builder.addHeaderText;
////    this.addContentText = builder.addContentText;
////    this.action = builder.action;
////    this.runnable = builder.runnable;
////    this.allFieldsCorrectlyFilledIn = builder.allFieldsCorrectlyFilledIn;
////    this.confirmEntityRemovalButton = builder.confirmEntityRemovalButton;
////    this.cancelButton = builder.cancelButton;
////  }
////
////  public Consumer<T> consumer(){
////    return consumer;
////  }
////
////  public T entityObject(){
////    return entityObject;
////  }
////
////  public String addHeaderText(){
////    return addHeaderText;
////  }
////
////  public String addContentText(){
////    return addContentText;
////  }
////
////  public ContinueNavigationAction action(){
////    return action;
////  }
////
////  public Runnable runnable(){
////    return runnable;
////  }
////
////  public boolean isAllFieldsCorrectlyFilledIn() {
////    return allFieldsCorrectlyFilledIn;
////  }
////
////  public Button confirmEntityRemovalButton(){
////    return confirmEntityRemovalButton;
////  }
////
////  public Button cancelButton(){
////    return cancelButton;
////  }
//
//
//}
//
//class ConfirmDialogx<T> extends Dialog {
//
//  private Consumer<T> consumer;
//  private T entityObject;
//  private String addHeaderText;
//  private String addContentText;
//  private ContinueNavigationAction action;
//  private Runnable runnable;
//  private boolean allFieldsCorrectlyFilledIn;
//  private Button confirmButton;
//  private Button cancelButton;
//
//  public ConfirmDialogx(Consumer<T> consumer, T entityObject, String addHeaderText,
//      String addContentText, ContinueNavigationAction action, Runnable runnable,
//      boolean allFieldsCorrectlyFilledIn, Button confirmButton,
//      Button cancelButton) {
//    this.consumer = consumer;
//    this.entityObject = entityObject;
//    this.addHeaderText = addHeaderText;
//    this.addContentText = addContentText;
//    this.action = action;
//    this.runnable = runnable;
//    this.allFieldsCorrectlyFilledIn = allFieldsCorrectlyFilledIn;
//    this.confirmButton = confirmButton;
//    this.cancelButton = cancelButton;
//  }
//}