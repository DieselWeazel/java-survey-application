package com.considlia.survey.ui.custom_component.ReadQuestionLayouts;

import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.answer.Answers;
import com.considlia.survey.model.answer.TextAnswer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.ReadQuestionComponent;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class ReadTextQuestionLayout extends ReadQuestionLayout implements ReadQuestionComponent {

  private TextAnswer textAnswer;
  private Binder<TextAnswer> binder;

  /**
   * Constructs a Layout for Viewing and Storing TextAnswer(s)
   * Works with both types of TextQuestion
   * @param question TextAreaQuestion or TextFieldQuestion
   */
  public ReadTextQuestionLayout(Question question) {
    super(question);
    TextField questionField = new TextField();
    TextArea questionArea = new TextArea();
    textAnswer = new TextAnswer();
    binder = new Binder<>(TextAnswer.class);
    binder.setBean(textAnswer);
    if (question.getQuestionType() == QuestionType.TEXTFIELD) {
      questionField.setWidth("40%");
      questionField.setEnabled(true);

      binder.forField(questionField).bind(TextAnswer::getTextAnswer, TextAnswer::setTextAnswer);
      add(questionField);
    } else if (question.getQuestionType() == QuestionType.TEXTAREA) {
      questionArea.setWidth("40%");
      questionArea.setHeight("80px");
      questionArea.setMaxHeight("100px");
      questionArea.setEnabled(true);

      binder.forField(questionArea).bind(TextAnswer::getTextAnswer, TextAnswer::setTextAnswer);
      add(questionArea);
    }
  }

  /**
   * Gathers Response of filled form.
   * @return TextAnswer
   * @throws ValidationException
   */
  @Override
  public Answers gatherResponse() throws ValidationException {
    textAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(textAnswer);
    getLOGGER().info("Logging answer: '{}'", textAnswer);

    return textAnswer;
  }
}
