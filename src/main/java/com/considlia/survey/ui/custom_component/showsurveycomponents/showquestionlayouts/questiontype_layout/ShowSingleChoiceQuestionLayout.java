package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.RadioAnswer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.ui.custom_component.showsurveycomponents.SurveyLoader;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

/**
 * Shows TextQuestions, inherits from the ShowQuestionLayout for base purposes.
 *
 * Written by Jonathan Harr
 */
public class ShowSingleChoiceQuestionLayout extends ShowQuestionLayout {

  private RadioButtonGroup<String> radioButtons;
  private RadioAnswer singleChoiceAnswer;
  private Binder<RadioAnswer> binder;

  /**
   * Constructs a Layout for Viewing and Storing RadioAnswer
   *
   * @param question RadioQuestion
   */
  public ShowSingleChoiceQuestionLayout(RadioQuestion question) {
    super(question);
    radioButtons = new RadioButtonGroup<>();
    binder = new Binder<>(RadioAnswer.class);
    singleChoiceAnswer = new RadioAnswer();
    binder.setBean(singleChoiceAnswer);

    radioButtons.setItems(question.getStringAlternatives());
    binder.forField(radioButtons).bind(RadioAnswer::getChosenAnswer, RadioAnswer::setChosenAnswer);

    add(radioButtons);
    radioButtons.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
  }


  /**
   * Inherited Method, sets the Binder to show a message if fields are empty and only if Question requires an answer.
   */
  public void setMandatoryStatus() {
    if (getQuestion().isMandatory()) {
      binder
          .forField(radioButtons)
          .withValidator(
              ratioAnswerString -> ratioAnswerString != null && !ratioAnswerString.isEmpty(),
              MANDATORY_QUESTION_MESSAGE)
          .bind(RadioAnswer::getChosenAnswer, RadioAnswer::setChosenAnswer);
    } else {
      binder
          .forField(radioButtons)
          .bind(RadioAnswer::getChosenAnswer, RadioAnswer::setChosenAnswer);
    }
  }

  /**
   * Gathers Response of filled form.
   *
   * @return RadioAnswer
   * @throws ValidationException
   */
  @Override
  public Answer gatherResponse() throws ValidationException {
    singleChoiceAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(singleChoiceAnswer);
    getLOGGER().info("Logging answer: '{}'", singleChoiceAnswer);
    return singleChoiceAnswer;
  }

  /**
   * Checks if RadioButtons are empty
   * @param question if question is mandatory
   * @return true if RadioButtons are filled in.
   */
  public boolean isCompleted(Question question) {
    getLOGGER().info("ShowMultiChoiceQuestionLayout isCompleted: '{}'", (!radioButtons.isEmpty()));
    return (!radioButtons.isEmpty());
  }
}
