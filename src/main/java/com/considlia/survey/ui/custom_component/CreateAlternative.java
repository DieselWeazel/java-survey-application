package com.considlia.survey.ui.custom_component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class CreateAlternative extends VerticalLayout {

  private TextField dynamicTextField;

  private List<String> alternativeList;
  private List<TextField> textFieldList;
  private QuestionType questionType;
  private CreateSurveyView csv;

  public CreateAlternative(QuestionType questionType, CreateSurveyView csv) {
    this.csv = csv;
    dynamicTextField = new TextField();

    textFieldList = new ArrayList<>();
    alternativeList = new ArrayList<>();

    createAlternative(questionType, alternativeList);

  }

  public void createAlternative(QuestionType questionType, List<String> alternativeList) {
    this.questionType = questionType;

    if (textFieldList.isEmpty()) {

      textFieldList.add(dynamicTextField);
      add(dynamicTextField);

      dynamicTextField.setPlaceholder("alternative");
      dynamicTextField.setValueChangeMode(ValueChangeMode.EAGER);
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
      dynamicTextField.addValueChangeListener(event -> {
        textFieldEvent(event);
      });
    }
  }

  public void textFieldEvent(ComponentValueChangeEvent<TextField, String> event) {
    if (event.getSource().getValue().isEmpty() && textFieldList.size() > 1) {
      remove(event.getSource());
      textFieldList.remove(event.getSource());
      textFieldList.get(textFieldList.size() - 1).focus();
    } else if (event.getSource().getValue().length() > 255) {
      event.getSource().setValue(event.getSource().getValue().substring(0, 255));
      Notification.show("Alternative can max contain 255 characters");
    }

    createAlternative(questionType, alternativeList);
    event.getSource().focus();

    boolean containsDuplicate = false;

    for (TextField t : textFieldList) {
      if (t.getValue().equals(event.getSource().getValue()) && t != event.getSource()) {
        containsDuplicate = true;
      }
    }

    // using Set to check if alternativeList contains duplicates
    Set<String> set = new LinkedHashSet<>();
    set.addAll(getAlternativeList());

    if (!getAlternativeList().isEmpty() && !csv.getQuestionTitleTextField().isEmpty()
        && set.size() == alternativeList.size()) {
      csv.getAddQuestionButton().setEnabled(true);
    } else {
      if (containsDuplicate && !getAlternativeList().isEmpty()) {
        Notification.show("Your alternatives contain duplicates");
      }
      csv.getAddQuestionButton().setEnabled(false);
    }
  }

  public List<String> getAlternativeList() {
    alternativeList.clear();
    for (TextField t : textFieldList) {
      if (!t.isEmpty()) {
        alternativeList.add(t.getValue());
      }
    }

    return alternativeList;
  }

  public QuestionType getQuestionType() {
    return questionType;
  }

  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
  }

}
