package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.MultiAnswer;
import com.considlia.survey.model.answer.RadioAnswer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.ui.custom_component.showsurveycomponents.ShowQuestionComponent;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.function.Consumer;

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

  public void setMandatoryStatus() {
    if (getQuestion().isMandatory()) {
      binder
          .forField(radioButtons)
          .withValidator(
              ratioAnswerString -> ratioAnswerString != null && !ratioAnswerString.isEmpty(),
              mandatoryQuestionMessage)
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
  public Answer gatherResponse() {
    singleChoiceAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    getLOGGER().info("Bean Valid: '{}'", binder.writeBeanIfValid(singleChoiceAnswer));
    getLOGGER().info("Logging answer: '{}'", singleChoiceAnswer);
    return singleChoiceAnswer;
  }

  public boolean isCompleted(Question question) {
    getLOGGER().info("ShowMultiChoiceQuestionLayout isCompleted: '{}'", (!radioButtons.isEmpty()));
    return (!radioButtons.isEmpty());
  }
}
