package com.considlia.survey.custom_component;

import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class CreateAlternative extends VerticalLayout {

  private TextField alternativeTextField;
  private TextField alternativeTextField2;
  private TextField dynamicTextField;

  private List<String> alternativeList;
  private List<TextField> textFieldList;
  private int typeOfQuestion;

  public CreateAlternative(int typeOfQuestion) {
    alternativeTextField = new TextField();
    alternativeTextField2 = new TextField();
    alternativeTextField2.setPlaceholder("Alternative " + "2");

    dynamicTextField = new TextField();
    dynamicTextField.setPlaceholder("dynamicTextField");

    textFieldList = new ArrayList<>();
    alternativeList = new ArrayList<>();

    createAlternative(typeOfQuestion, alternativeList);

  }

  public List<String> createAlternative(int typeOfQuestion, List<String> alternativeList) {
    this.typeOfQuestion = typeOfQuestion;
    System.out.println("createAlternative() invoked");

    if (alternativeList.isEmpty()) {
      textFieldList.add(alternativeTextField);

      System.out.println("alternativeList is empty");
      alternativeTextField.setValueChangeMode(ValueChangeMode.EAGER);
      alternativeTextField.addValueChangeListener(event -> {
        try {
          alternativeList.set(0, textFieldList.get(0).getValue());
          if (event.getSource().getValue().isEmpty()) {
            alternativeList.remove(0);
            textFieldList.remove(0);
          }
        } catch (IndexOutOfBoundsException e) {
          alternativeList.add(0, textFieldList.get(0).getValue());
        }
        if (event.getSource().getValue().isEmpty() && alternativeList.size() < 3) {
          alternativeTextField2.setEnabled(false);
        } else {
          alternativeTextField2.setEnabled(true);
        }
      });

      textFieldList.add(alternativeTextField2);
      alternativeTextField2.setEnabled(false);
      alternativeTextField2.setValueChangeMode(ValueChangeMode.EAGER);
      alternativeTextField2.addValueChangeListener(event -> {
        try {
          alternativeList.set(1, textFieldList.get(1).getValue());
          if (event.getSource().getValue().isEmpty()) {
            alternativeList.remove(1);
            textFieldList.remove(1);
          }
        } catch (IndexOutOfBoundsException e) {
          alternativeList.add(1, textFieldList.get(1).getValue());
        }
        createAlternative(typeOfQuestion, alternativeList);
        alternativeTextField2.focus();
      });
      setTextFieldListGui();
    } else {
      bothListsNotEmpty();
    }
    return alternativeList;
  }

  public void bothListsNotEmpty() {
    // _________________________ALTERNATIVELIST AND TEXTFIELDLIST NOT EMPTY________________
    System.out.println("both lists not empty");

    // checking if the lists are the same size and if the last textField i empty
    if (textFieldList.size() == alternativeList.size()
        && !textFieldList.get(textFieldList.size() - 1).isEmpty()) {
      int currentSize = alternativeList.size();
      dynamicTextField = new TextField();
      textFieldList.add(dynamicTextField);
      dynamicTextField.setValueChangeMode(ValueChangeMode.EAGER);
      dynamicTextField.addValueChangeListener(event -> {
        try {
          alternativeList.set(currentSize, event.getValue());
          if (event.getSource().getValue().isEmpty()) {
            System.out.println("IS EMPTY " + currentSize);
            alternativeList.remove(currentSize);
            textFieldList.remove(currentSize);
          }
        } catch (IndexOutOfBoundsException e) {
          System.out.println("IndexOutOfBoundsException caught");
          alternativeList.add(currentSize, event.getValue());
        }
        createAlternative(typeOfQuestion, alternativeList);
        event.getSource().focus();
      });
      setTextFieldListGui();
    }
  }

  // retunera en verticallayour. lägg till allt i verticallayout istället
  public void setTextFieldListGui() {

    for (TextField t : textFieldList) {
      remove(t);
    }

    if (textFieldList.size() <= 2) {
      for (TextField t : textFieldList) {
        add(t);
      }
    } else {
      for (TextField t : textFieldList) {
        if (!t.getValue().isEmpty() || textFieldList.get(textFieldList.size() - 1) == t) {
          add(t);
        }
      }
    }
  }

  public List<String> getAlternativeList() {
    return alternativeList;
  }

  public void setAlternativeList(List<String> alternativeList) {
    this.alternativeList = alternativeList;
  }

}
