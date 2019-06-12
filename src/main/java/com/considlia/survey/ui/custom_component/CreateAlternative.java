package com.considlia.survey.ui.custom_component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.considlia.survey.model.QuestionType;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class CreateAlternative extends VerticalLayout {

  private TextField dynamicTextField;

  private List<String> alternativeList;
  private List<TextField> textFieldList;
  private QuestionType questionType;
  private CreateSurveyView csv;

  /**
   * Constructor adds new empty TextField to variable dynamicTextField. Adds new empty ArrayLists to
   * private Lists alternativeList and textFieldList. Invokes createAlternative with given
   * questionType and empty arrayList.
   *
   * @param csv Instance of current CreateSurveyView to getList access to addQuestionButton.
   */
  public CreateAlternative(CreateSurveyView csv) {
    this.csv = csv;
    dynamicTextField = new TextField();

    textFieldList = new ArrayList<>();
    alternativeList = new ArrayList<>();

    createAlternative(alternativeList);

  }

  /**
   * Invoked first time when user chooses questionType RADIO or CHECKBOX, then invoked with every
   * TextField event. Method manages the length of textFieldList and creates new TextFields. Adds a
   * new {@link TextField} if textFieldList is empty or the last TextField in TextFieldList contains
   * value. Adds event on all new TextFields using {@link addValueChangeListener} with
   * {@link ValueChangeMode.EAGER}
   *
   * @param alternativeList
   */
  private void createAlternative(List<String> alternativeList) {

    if (textFieldList.isEmpty()) {

      textFieldList.add(dynamicTextField);
      add(dynamicTextField);

      dynamicTextField.setPlaceholder("alternative");
      dynamicTextField.setValueChangeMode(ValueChangeMode.EAGER);
      dynamicTextField.addBlurListener(blurevent -> blurTextFieldEvent(blurevent));
      dynamicTextField.addValueChangeListener(event -> {
        textFieldEvent(event);
      });
    } else if (!textFieldList.get(textFieldList.size() - 1).isEmpty()) {
      // checked if the last textField in list is not empty

      dynamicTextField = new TextField();

      textFieldList.add(dynamicTextField);
      add(dynamicTextField);

      dynamicTextField.setPlaceholder("alternative");
      dynamicTextField.setValueChangeMode(ValueChangeMode.EAGER);
      dynamicTextField.addBlurListener(blurevent -> blurTextFieldEvent(blurevent));
      dynamicTextField.addValueChangeListener(event -> {
        textFieldEvent(event);
      });
    }
  }

  /**
   * Invoked every time user changes value in textField. If value of {@link TextField} is empty and
   * there's more than one TextField in textFieldList the TextField is removed. Checks if the
   * {@link String} is more than 255 characters. Puts out notification if alternativeList contains
   * duplicates. Also manages enable/disable for addQuestionButton.
   *
   * @param event, used to getList value and source
   */
  private void textFieldEvent(ComponentValueChangeEvent<TextField, String> event) {
    if (event.getSource().getValue().isEmpty() && textFieldList.size() > 1) {
      remove(event.getSource());
      textFieldList.remove(event.getSource());
      textFieldList.get(textFieldList.size() - 1).focus();
    } else if (event.getSource().getValue().length() > 255) {
      event.getSource().setValue(event.getSource().getValue().substring(0, 255));
      Notification.show("Alternative can max contain 255 characters", 2000, Position.MIDDLE);
    }

    createAlternative(alternativeList);
    event.getSource().focus();

    // using Set to check if alternativeList contains duplicates
    Set<String> set = new LinkedHashSet<>();
    set.addAll(getAlternativeList());

    // if the alternativeList is not empty and questionTitleField is not empty
    // and no duplicates exists
    if (!getAlternativeList().isEmpty() && !csv.getQuestionTitleTextField().isEmpty()
        && set.size() == alternativeList.size()) {

      // if the questionType is radio and there are less than two alternatives
      if (getQuestionType() == QuestionType.RADIO && getAlternativeList().size() < 2) {
        csv.getAddQuestionButton().setEnabled(false);
      } else {
        csv.getAddQuestionButton().setEnabled(true);
      }

    } else {
      csv.getAddQuestionButton().setEnabled(false);
    }
  }

  /**
   * This method manages the notification if the alternatives contain duplicates. The blurevent is
   * triggered when the component loses focus.
   * 
   * @param blurevent
   */
  private void blurTextFieldEvent(BlurEvent<TextField> blurevent) {
    boolean containsDuplicate = false;

    for (TextField t : textFieldList) {
      if (t.getValue().equals(blurevent.getSource().getValue()) && t != blurevent.getSource()) {
        containsDuplicate = true;
      }
    }
    if (containsDuplicate && !getAlternativeList().isEmpty()) {
      Notification.show("Your alternatives contain duplicates", 2000, Position.MIDDLE);
    }
  }

  /**
   *
   * @returns {@link List} containing Strings with values from TextFields in textFieldList.
   */
  public List<String> getAlternativeList() {
    alternativeList.clear();
    for (TextField t : textFieldList) {
      if (!t.isEmpty()) {
        alternativeList.add(t.getValue());
      }
    }

    return alternativeList;
  }

  /**
   *
   * @returns QuestionType RADIO or CHECKBOX
   */
  public QuestionType getQuestionType() {
    return questionType;
  }

  /**
   * Sets QuestionType
   *
   * @param questionType
   */
  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
  }

}
