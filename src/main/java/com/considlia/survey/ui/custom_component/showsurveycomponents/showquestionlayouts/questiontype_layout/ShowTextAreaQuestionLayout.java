package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.TextAnswer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.showsurveycomponents.ShowQuestionComponent;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.StringLengthValidator;

public class ShowTextAreaQuestionLayout extends ShowQuestionLayout
    implements ShowQuestionComponent {

  private TextAnswer textAnswer;
  private Binder<TextAnswer> binder;
  private TextArea questionArea = new TextArea();
  /**
   * Constructs a Layout for Viewing and Storing TextAnswer(s) Works with both types of TextQuestion
   *
   * @param question TextAreaQuestion or TextFieldQuestion
   */
  public ShowTextAreaQuestionLayout(Question question) {
    super(question);

    textAnswer = new TextAnswer();
    binder = new Binder<>(TextAnswer.class);
    binder.setBean(textAnswer);

    questionArea.setWidth("40%");
    questionArea.setHeight("80px");
    questionArea.setMaxHeight("100px");
    questionArea.setEnabled(true);

    add(questionArea);
  }

  @Override
  public void setMandatoryStatus() {
    if (getQuestion().isMandatory()) {
      binder
          .forField(questionArea)
          .withValidator(new StringLengthValidator(mandatoryQuestionMessage, 1, null))
          .bind(TextAnswer::getTextAnswer, TextAnswer::setTextAnswer);
    } else {
      binder.forField(questionArea).bind(TextAnswer::getTextAnswer, TextAnswer::setTextAnswer);
    }
  }

  /**
   * Gathers Response of filled form.
   *
   * @return TextAnswer
   * @throws ValidationException
   */
  @Override
  public Answer gatherResponse() throws ValidationException {
    textAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(textAnswer);
    getLOGGER().info("Logging answer: '{}'", textAnswer);

    return textAnswer;
  }
}
