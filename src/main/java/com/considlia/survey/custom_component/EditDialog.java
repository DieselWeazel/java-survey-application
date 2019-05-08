package com.considlia.survey.custom_component;

import java.util.ArrayList;
import java.util.List;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class EditDialog extends Dialog {

  private VerticalLayout contentBox = new VerticalLayout();
  private VerticalLayout footer = new VerticalLayout();
  private TextField question;
  private Button confirm, inputButton;
  private List<TextField> textFieldList = new ArrayList<>();

  public EditDialog(Button button) {
    open();
    setCloseOnEsc(false);
    setCloseOnOutsideClick(false);

    inputButton = button;
    question = new TextField("Question:");
    contentBox.add(question);

    if (button.getParent().get().getParent().get() instanceof TextQuestionWithButtons) {
      textQuestion();
    } else {
      radioQuestion();

    }

    footer.add(new HorizontalLayout(new Button("Cancel", onCancel -> close()), confirm));
    add(contentBox, footer);
  }

  public void textQuestion() {
    TextQuestionWithButtons choosenQuestion =
        (TextQuestionWithButtons) inputButton.getParent().get().getParent().get();
    question.setValue(choosenQuestion.getQuestion());

    confirm = new Button("Confirm");
    confirm.addClickListener(onConfirm -> {
      choosenQuestion.setQuestion(question.getValue());
      close();
    });

  }

  public void radioQuestion() {
    RadioQuestionWithButtons choosenQuestion =
        (RadioQuestionWithButtons) inputButton.getParent().get().getParent().get();
    question.setValue(choosenQuestion.getQuestion());

    List<MultiQuestionAlternative> alternativeList = new ArrayList<>();
    for (MultiQuestionAlternative alternative : choosenQuestion.getAlternatives()) {
      alternativeList.add(alternative);
    }

    // Sorting the list after the position index
    alternativeList.sort((MultiQuestionAlternative m1,
        MultiQuestionAlternative m2) -> m1.getPosition() - m2.getPosition());

    for (MultiQuestionAlternative alternative : alternativeList) {
      addNewTextField(alternative.getAlternativeTitle());
    }

    footer.add(new Button(new Icon(VaadinIcon.PLUS_CIRCLE), event -> addNewTextField(null)));
    confirm = new Button("Confirm");
    confirm.addClickListener(onConfirm -> {
      choosenQuestion.setQuestion(question.getValue());

      List<String> strings = new ArrayList<>();
      for (TextField txt : textFieldList) {
        if (!txt.getValue().trim().equals("")) {
          strings.add(txt.getValue());

        }
      }
      if (!strings.isEmpty()) {
        choosenQuestion.setStringAlternatives(strings);
        close();
      } else {
        Dialog info = new Dialog();
        info.open();
        info.add("Need to have at least 1 alternative");
      }

    });
  }

  public void addNewTextField(String title) {
    HorizontalLayout horizontalBox = new HorizontalLayout();
    TextField txtAlternative = new TextField("Alternative " + (contentBox.getComponentCount()));
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

  public void remove(Button b) {
    contentBox.remove(b.getParent().get());
    HorizontalLayout h = (HorizontalLayout) b.getParent().get();

    textFieldList.remove(h.getComponentAt(0));
    for (int i = 0; i < textFieldList.size(); i++) {
      textFieldList.get(i).setLabel("Alternative " + (i + 1));
    }

  }

}
