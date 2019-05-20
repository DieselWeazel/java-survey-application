package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Question;
import com.considlia.survey.model.QuestionType;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class ReadTextQuestionLayout extends ReadQuestionLayout {

  private TextField questionField = new TextField();
  private TextArea questionArea = new TextArea();

  public ReadTextQuestionLayout(Question question) {
    super(question);

    if (question.getQuestionType() == QuestionType.TEXTFIELD) {
      questionField.setWidth("40%");
      questionField.setEnabled(true);
      add(questionField);
    } else if (question.getQuestionType() == QuestionType.TEXTAREA) {
      questionArea.setWidth("40%");
      questionArea.setHeight("80px");
      questionArea.setMaxHeight("100px");
      questionArea.setEnabled(true);
      add(questionArea);
    }
  }

  public String getQuestionField() {
    return questionField.getValue();
  }

  public String getQuestionArea() {
    return questionArea.getValue();
  }

}
