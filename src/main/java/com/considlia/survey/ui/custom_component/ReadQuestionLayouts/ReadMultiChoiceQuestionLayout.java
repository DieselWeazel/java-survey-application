package com.considlia.survey.ui.custom_component.ReadQuestionLayouts;

import com.considlia.survey.model.answer.Answers;
import com.considlia.survey.model.answer.ChosenAnswer;
import com.considlia.survey.model.answer.MultiChoiceAnswer;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.MultiQuestionAlternative;
import com.considlia.survey.ui.custom_component.ReadQuestionComponent;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class ReadMultiChoiceQuestionLayout extends ReadQuestionLayout implements
    ReadQuestionComponent {

  private MultiChoiceAnswer multiChoiceAnswer;
  private Binder<MultiChoiceAnswer> binder;

  private List<ChosenAnswer> answerAlternativeList = new ArrayList<ChosenAnswer>();

  /**
   * Constructs a Layout for Viewing and Storing MultiChoiceAnswer(s)
   * @param question MultiQuestion only
   */
  public ReadMultiChoiceQuestionLayout(MultiQuestion question) {
    super(question);
      CheckboxGroup<ChosenAnswer> checkBoxButtons = new CheckboxGroup<>();
      binder = new Binder<>(MultiChoiceAnswer.class);
      multiChoiceAnswer = new MultiChoiceAnswer();
      binder.setBean(multiChoiceAnswer);
      
      question.getStringAlternatives().forEach(e ->
          answerAlternativeList.add(new ChosenAnswer(e)));

      checkBoxButtons.setItems(answerAlternativeList);
      binder.forField(checkBoxButtons).bind(MultiChoiceAnswer::getChosenAnswerSet, MultiChoiceAnswer::setChosenAnswerSet);

      add(checkBoxButtons);
      checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    }

  /**
   * Gathers Response of filled form.
   * @return MultiChoiceAnswer
   * @throws ValidationException
   */
  @Override
  public Answers gatherResponse() throws ValidationException {
    multiChoiceAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(multiChoiceAnswer);
    answerAlternativeList.forEach(e-> getLOGGER().info("Logging chosenanswer: '{}'", e.toString()));
    getLOGGER().info("Logging answer: '{}'", multiChoiceAnswer);

    return multiChoiceAnswer;
  }
}
