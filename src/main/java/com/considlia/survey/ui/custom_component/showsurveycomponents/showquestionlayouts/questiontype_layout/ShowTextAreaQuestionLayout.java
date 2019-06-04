package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.TextAnswer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.StringLengthValidator;

/**
 * Shows TextQuestions, inherits from the ShowQuestionLayout for base purposes.
 *
 * Written by Jonathan Harr
 */
public class ShowTextAreaQuestionLayout extends ShowQuestionLayout {

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

  /**
   * Inherited Method, sets the Binder to show a message if fields are empty and only if Question requires an answer.
   */
  public void setMandatoryStatus() {
    if (getQuestion().isMandatory()) {
      binder
          .forField(questionArea)
          .withValidator(new StringLengthValidator(MANDATORY_QUESTION_MESSAGE, 1, null))
          .bind(TextAnswer::getTextAnswer, TextAnswer::setTextAnswer);
      if (questionArea.getValue().length()<1){
      }
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
    getLOGGER().info("Bean Valid: '{}'", binder.writeBeanIfValid(textAnswer));
    binder.writeBean(textAnswer);
    getLOGGER().info("Logging answer: '{}'", textAnswer);
    return textAnswer;
  }

  /**
   * Checks if QuetionArea is empty
   * @param question if question is mandatory
   * @return true if QuestionArea is filled in.
   */
  public boolean isCompleted(Question question) {
//    getLOGGER().info("ShowMultiChoiceQuestionLayout isCompleted: '{}'", (!questionArea.isEmpty()));
    return (!questionArea.isEmpty());
  }
}
