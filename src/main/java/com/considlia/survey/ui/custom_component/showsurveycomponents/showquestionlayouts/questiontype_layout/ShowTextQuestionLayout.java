package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.TextAnswer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.showsurveycomponents.ShowQuestionComponent;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.StringLengthValidator;
import java.util.function.Consumer;

public class ShowTextQuestionLayout extends ShowQuestionLayout
    implements ShowQuestionComponent {

  private TextAnswer textAnswer;
  private Binder<TextAnswer> binder;
  private TextField questionField = new TextField();
  /**
   * Constructs a Layout for Viewing and Storing TextAnswer(s) Works with both types of TextQuestion
   *
   * @param question TextAreaQuestion or TextFieldQuestion
   */
  public ShowTextQuestionLayout(Question question) {
    super(question);

    textAnswer = new TextAnswer();
    binder = new Binder<>(TextAnswer.class);
    binder.setBean(textAnswer);

    questionField.setWidth("40%");
    questionField.setEnabled(true);

    binder.forField(questionField).bind(TextAnswer::getTextAnswer, TextAnswer::setTextAnswer);
    add(questionField);
  }


  @Override
  public void setMandatoryStatus() {
    if (getQuestion().isMandatory()) {
      binder
          .forField(questionField)
          .withValidator(new StringLengthValidator(mandatoryQuestionMessage, 1, null))
          .bind(TextAnswer::getTextAnswer, TextAnswer::setTextAnswer);
    } else {
      binder.forField(questionField).bind(TextAnswer::getTextAnswer, TextAnswer::setTextAnswer);
    }
  }

  /**
   * Gathers Response of filled form.
   *
   * @return TextAnswer
   * @throws ValidationException
   */
  @Override
  public Answer gatherResponse(Consumer<Answer> consumer) throws ValidationException {
    textAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    getLOGGER().info("Bean Valid: '{}'", binder.writeBeanIfValid(textAnswer));
    if (binder.writeBeanIfValid(textAnswer)) {
      getLOGGER().info("Logging answer: '{}'", textAnswer);
      return textAnswer;
    } else {
      consumer.accept(textAnswer);
    }
    return null;
    }
}
