package com.considlia.survey.ui.custom_component;

import java.util.ArrayList;
import java.util.List;
import com.considlia.survey.model.question.MultiQuestionAlternative;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.question_with_button.MultiQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.QuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.RatioQuestionWithButtons;
import com.considlia.survey.ui.custom_component.question_with_button.TextQuestionWithButtons;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class EditDialog extends Dialog {

  private VerticalLayout contentBox = new VerticalLayout();
  private VerticalLayout footer = new VerticalLayout();
  private TextField question;
  private Button confirm, inputButton;
  private List<TextField> textFieldList = new ArrayList<>();
  private Checkbox mandatory;

  /**
   * Creates and opens a dialog component. Depending on the type of the paramater set the dialog
   * body with different components
   *
   * @param button from which the data is taken from
   */
  public EditDialog(Button button) {
    open();
    setCloseOnEsc(false);
    setCloseOnOutsideClick(false);

    inputButton = button;
    question = new TextField("Question:");
    contentBox.add(question);

    mandatory = new Checkbox("Mandatory Question");
    contentBox.add(mandatory);

    if (button.getParent().get().getParent().get() instanceof TextQuestionWithButtons) {
      textQuestion();
    } else if (button.getParent().get().getParent().get() instanceof RatioQuestionWithButtons) {
      ratioQuestion();
    } else {
      radioQuestion();
    }

    footer.add(new HorizontalLayout(new Button("Cancel", onCancel -> close()), confirm));
    add(contentBox, footer);
  }

  /**
   * Adds the text specific TextFields to the dialog content.
   * <p>
   * Confirm Button is created and {@link ClickEvent} is added
   */
  private void textQuestion() {
    TextQuestionWithButtons choosenQuestion =
        (TextQuestionWithButtons) inputButton.getParent().get().getParent().get();
    setCommonValues(choosenQuestion);

    confirm = new Button("Confirm");
    confirm.addClickListener(onConfirm -> {
      updateCommonValues(choosenQuestion);
      close();
    });

  }

  /**
   * Adds the raito specific TextFields and numberFields to the dialog content.
   * <p>
   * Confirm Button is created and {@link ClickEvent} is added
   */
  private void ratioQuestion() {
    RatioQuestionWithButtons choosenQuestion =
        (RatioQuestionWithButtons) inputButton.getParent().get().getParent().get();
    setCommonValues(choosenQuestion);

    TextField startLimit =
        constructRatioTextFields("The value of the first option", choosenQuestion.getStart());
    TextField endLimit =
        constructRatioTextFields("The value of the last option", choosenQuestion.getEnd());

    NumberField stepperField = new NumberField();
    stepperField.setLabel("Number of options");
    stepperField.getStyle().set("width", "140px");
    stepperField.setValue((double) choosenQuestion.getChoices());
    stepperField.setMin(2);
    stepperField.setMax(10);
    stepperField.setHasControls(true);

    contentBox.add(startLimit, endLimit, stepperField);

    confirm = new Button("Confirm");
    confirm.addClickListener(onConfirm -> {
      choosenQuestion.setStart(startLimit.getValue().trim());
      choosenQuestion.setEnd(endLimit.getValue().trim());
      choosenQuestion.setChoices((int) Math.round(stepperField.getValue()));
      updateCommonValues(choosenQuestion);
      choosenQuestion.updateRadioOptions();

      close();
    });
  }

  /**
   * Constructs a {@link TextField} with label and value set from the parameters. ValueChangeMode is
   * set to EAGER. Has a {@link ValueChangeListener}
   * 
   * 
   * @param labelInput - {@link String} value for the label
   * @param valueInput - {@link String} value for the value
   * @return constructed {@link TextField}
   */
  public TextField constructRatioTextFields(String labelInput, String valueInput) {
    TextField ratioTxtField = new TextField(labelInput);
    ratioTxtField.setValue(valueInput);
    ratioTxtField.setValueChangeMode(ValueChangeMode.EAGER);
    ratioTxtField.addValueChangeListener(onValueChange -> showValidNotification(ratioTxtField));
    return ratioTxtField;

  }

  /**
   * Adds the Radio specific TextFields to the dialog content.
   * <p>
   * Confirm Button is created and {@link ClickEvent} is added
   */
  private void radioQuestion() {
    MultiQuestionWithButtons choosenQuestion =
        (MultiQuestionWithButtons) inputButton.getParent().get().getParent().get();
    setCommonValues(choosenQuestion);

    List<MultiQuestionAlternative> alternativeList = new ArrayList<>();
    for (MultiQuestionAlternative alternative : choosenQuestion.getQuestion().getAlternatives()) {
      alternativeList.add(alternative);
    }

    // Sorting the list after the position index
    alternativeList.sort((MultiQuestionAlternative m1,
        MultiQuestionAlternative m2) -> m1.getPosition() - m2.getPosition());

    for (MultiQuestionAlternative alternative : alternativeList) {
      addNewTextField(alternative.getTitle());
    }

    footer.add(new Button(new Icon(VaadinIcon.PLUS_CIRCLE), event -> addNewTextField(null)));
    confirm = new Button("Confirm");
    confirm.addClickListener(onConfirm -> {
      updateCommonValues(choosenQuestion);

      List<String> strings = new ArrayList<>();
      for (TextField txt : textFieldList) {
        if (!txt.getValue().trim().equals("")) {
          strings.add(txt.getValue().trim());

        }
      }
      if (!strings.isEmpty()) {
        choosenQuestion.updateGUI(strings);
        close();
      } else {
        Dialog info = new Dialog();
        info.open();
        info.add("Need to have at least 1 alternative");
      }

    });
  }

  /**
   * Creates a new {@link HorizontalLayout} containing a {@link TextField} and {@link Button}.
   * Button will remove this HorizontalLayout
   *
   * @param title is used for the TextFields value
   */
  private void addNewTextField(String title) {
    HorizontalLayout horizontalBox = new HorizontalLayout();
    TextField txtAlternative = new TextField("Alternative " + (contentBox.getComponentCount() - 1));
    if (title != null) {
      txtAlternative.setValue(title);
    }
    Button remove = new Button(new Icon(VaadinIcon.MINUS));
    remove.addClickListener(onRemove -> {
      remove(remove);
    });
    horizontalBox.add(txtAlternative, remove);
    horizontalBox.setAlignItems(Alignment.BASELINE);
    contentBox.add(horizontalBox);
    textFieldList.add(txtAlternative);
  }

  /**
   * Removes the given components from this component.
   *
   * @param b is the components to remove
   */
  private void remove(Button b) {
    contentBox.remove(b.getParent().get());
    HorizontalLayout h = (HorizontalLayout) b.getParent().get();

    textFieldList.remove(h.getComponentAt(0));
    for (int i = 0; i < textFieldList.size(); i++) {
      textFieldList.get(i).setLabel("Alternative " + (i + 1));
    }
  }

  /**
   * Shows a {@link Notification} with a error message if the requirements aren't met.
   * 
   * @param txtField - {@link TextField}
   */
  public void showValidNotification(TextField txtField) {
    String errorMessage = "";
    if (txtField.getValue().trim().length() < 1 || txtField.getValue().length() >= 255) {
      errorMessage += "Can't be empty and can only contain 255 characters.";
    }
    if (txtField.getValue().chars().anyMatch(Character::isDigit)) {
      errorMessage += "Can contain any numbers. ";
    }
    if (!errorMessage.equals("")) {
      confirm.setEnabled(false);
      new Notification(errorMessage, 2000).open();
    } else {
      confirm.setEnabled(true);

    }
  }

  /**
   * Sets the values that every {@link QuestionWithButtons} have in common (title and if its
   * mandatory)
   *
   * @param choosenQuestion is the component from which the data is taken from
   */
  private void setCommonValues(QuestionWithButtons choosenQuestion) {
    question.setValue(choosenQuestion.getQuestion().getTitle().trim());
    mandatory.setValue(choosenQuestion.getQuestion().isMandatory());
  }

  /**
   * Sets the values that every {@link Question} have in common (title and if its mandatory)
   *
   * @param choosenQuestion is the component that'll be updated
   */
  private void updateCommonValues(QuestionWithButtons choosenQuestion) {
    choosenQuestion.getQuestion().setMandatory(mandatory.getValue());
    choosenQuestion.getQuestion().setTitle(question.getValue());
    choosenQuestion.setTitleInUI();
  }

}
