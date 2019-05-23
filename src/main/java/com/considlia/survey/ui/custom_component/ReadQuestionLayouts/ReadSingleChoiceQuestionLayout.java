package com.considlia.survey.ui.custom_component.ReadQuestionLayouts;

import com.considlia.survey.model.answer.Answers;
import com.considlia.survey.model.answer.ChosenAnswer;
import com.considlia.survey.model.answer.MultiChoiceAnswer;
import com.considlia.survey.model.answer.SingleChoiceAnswer;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.MultiQuestionAlternative;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.ReadQuestionComponent;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class ReadSingleChoiceQuestionLayout extends ReadQuestionLayout implements
    ReadQuestionComponent {

  private RadioButtonGroup<ChosenAnswer> radioButtons;
  private SingleChoiceAnswer singleChoiceAnswer;
  private Binder<SingleChoiceAnswer> binder;

  private List<ChosenAnswer> answerAlternativeList = new ArrayList<ChosenAnswer>();

  public ReadSingleChoiceQuestionLayout(MultiQuestion question) {
    super(question);
    radioButtons = new RadioButtonGroup<>();
    binder = new Binder<>(SingleChoiceAnswer.class);
    singleChoiceAnswer = new SingleChoiceAnswer();
    binder.setBean(singleChoiceAnswer);

    // TODO Duplicate CODE
    question.getStringAlternatives().forEach(e ->
        answerAlternativeList.add(new ChosenAnswer(e)));

    // might be able to delete setItems?
    radioButtons.setItems(answerAlternativeList);
    binder.forField(radioButtons).bind(SingleChoiceAnswer::getChosenAnswer, SingleChoiceAnswer::setChosenAnswer);

    add(radioButtons);
    radioButtons.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
  }

  @Override
  public Answers gatherResponse() throws ValidationException {
    singleChoiceAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(singleChoiceAnswer);
    answerAlternativeList.forEach(e-> getLOGGER().info("Logging chosenanswer: '{}'", e.toString()));
    getLOGGER().info("Logging answer: '{}'", singleChoiceAnswer);

    return singleChoiceAnswer;
  }
}
