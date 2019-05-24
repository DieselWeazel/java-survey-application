package com.considlia.survey.ui.custom_component.ShowSurveyComponents.ReadQuestionLayouts;

import com.considlia.survey.model.answer.Answers;
import com.considlia.survey.model.answer.RadioAnswer;
import com.considlia.survey.model.question.RadioQuestion;
import com.considlia.survey.ui.custom_component.ShowSurveyComponents.ReadQuestionComponent;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class ReadSingleChoiceQuestionLayout extends ReadQuestionLayout implements
    ReadQuestionComponent {

  private RadioButtonGroup<String> radioButtons;
  private RadioAnswer singleChoiceAnswer;
  private Binder<RadioAnswer> binder;

  /**
   * Constructs a Layout for Viewing and Storing RadioAnswer
   * @param question RadioQuestion
   */
  public ReadSingleChoiceQuestionLayout(RadioQuestion question) {
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
   * Gathers Response of filled form.
   * @return RadioAnswer
   * @throws ValidationException
   */
  @Override
  public Answers gatherResponse() throws ValidationException {
    singleChoiceAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(singleChoiceAnswer);
    getLOGGER().info("Logging answer: '{}'", singleChoiceAnswer);

    return singleChoiceAnswer;
  }
}
