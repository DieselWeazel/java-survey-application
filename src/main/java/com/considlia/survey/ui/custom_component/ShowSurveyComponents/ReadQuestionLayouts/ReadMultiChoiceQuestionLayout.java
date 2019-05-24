package com.considlia.survey.ui.custom_component.ShowSurveyComponents.ReadQuestionLayouts;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.MultiAnswerChoice;
import com.considlia.survey.model.answer.MultiAnswer;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.ui.custom_component.ShowSurveyComponents.ReadQuestionComponent;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class ReadMultiChoiceQuestionLayout extends ReadQuestionLayout implements
    ReadQuestionComponent {

  private MultiAnswer multiChoiceAnswer;
  private Binder<MultiAnswer> binder;

  private List<MultiAnswerChoice> answerAlternativeList = new ArrayList<MultiAnswerChoice>();

  /**
   * Constructs a Layout for Viewing and Storing MultiAnswer(s)
   * @param question MultiQuestion only
   */
  public ReadMultiChoiceQuestionLayout(MultiQuestion question) {
    super(question);
      CheckboxGroup<MultiAnswerChoice> checkBoxButtons = new CheckboxGroup<>();
      binder = new Binder<>(MultiAnswer.class);
      multiChoiceAnswer = new MultiAnswer();
      binder.setBean(multiChoiceAnswer);

      question.getStringAlternatives().forEach(e ->
          answerAlternativeList.add(new MultiAnswerChoice(e)));

      checkBoxButtons.setItems(answerAlternativeList);
      binder.forField(checkBoxButtons).bind(MultiAnswer::getMultiAnswerChoiceSet, MultiAnswer::setMultiAnswerChoiceSet);

      add(checkBoxButtons);
      checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    }

  /**
   * Gathers Response of filled form.
   * @return MultiAnswer
   * @throws ValidationException
   */
  @Override
  public Answer gatherResponse() throws ValidationException {
    multiChoiceAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(multiChoiceAnswer);
    answerAlternativeList.forEach(e-> getLOGGER().info("Logging chosenanswer: '{}'", e.toString()));
    getLOGGER().info("Logging answer: '{}'", multiChoiceAnswer);

    return multiChoiceAnswer;
  }
}
