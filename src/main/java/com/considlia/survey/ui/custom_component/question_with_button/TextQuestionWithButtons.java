package com.considlia.survey.ui.custom_component.question_with_button;

import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.question.TextQuestion;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Class extending {@link QuestionWithButtons}. Contains the specific {@link Component}s for the
 * subclasses of {@link TextQuestion}
 *
 */
@StyleSheet("css/app.css")
public class TextQuestionWithButtons extends QuestionWithButtons {

  /**
   * Calls the super constructor. Also adds either a {@link TextField} or {@link TextArea} depending
   * on the {@link QuestionType} from the {@link TextQuestion#getQuestionType()}
   * 
   * @param question getEntity {@link QuestionType} and also for passing it to super
   * @param survey passing it to super
   */
  public TextQuestionWithButtons(TextQuestion question, CreateSurveyView survey) {
    super(question, survey);

    if (question.getQuestionType() == QuestionType.TEXTFIELD) {
      TextField textfield = new TextField();
      textfield.setWidth("40%");
      textfield.setEnabled(false);
      add(textfield);
    } else if (question.getQuestionType() == QuestionType.TEXTAREA) {
      TextArea textarea = new TextArea();
      textarea.setWidth("40%");
      textarea.setHeight("35%");
      textarea.setEnabled(false);
      add(textarea);
    }
  }
}
