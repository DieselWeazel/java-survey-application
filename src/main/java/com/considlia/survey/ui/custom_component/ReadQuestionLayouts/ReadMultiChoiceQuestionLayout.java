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

  private CheckboxGroup<ChosenAnswer> checkBoxButtons;
  private MultiChoiceAnswer multiChoiceAnswer;
  private Binder<MultiChoiceAnswer> binder;

  private List<ChosenAnswer> answerAlternativeList = new ArrayList<ChosenAnswer>();

  public ReadMultiChoiceQuestionLayout(MultiQuestion question) {
    super(question);
      checkBoxButtons = new CheckboxGroup<>();
      binder = new Binder<>(MultiChoiceAnswer.class);
      multiChoiceAnswer = new MultiChoiceAnswer();
      binder.setBean(multiChoiceAnswer);

      question.getStringAlternatives().forEach(e ->
          answerAlternativeList.add(new ChosenAnswer(e)));

      answerAlternativeList.addAll(answerAlternativeList);


      binder.forField(checkBoxButtons).bind(MultiChoiceAnswer::getChosenAnswerSet, MultiChoiceAnswer::setChosenAnswerSet);
      add(checkBoxButtons);
      checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    }

  @Override
  public Answers gatherResponse() throws ValidationException {
    multiChoiceAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(multiChoiceAnswer);
    getLOGGER().info("Logging answer: '{}'", multiChoiceAnswer);

    return multiChoiceAnswer;
  }
}
//  List<Integer> data = Arrays.asList(0, 1, 2, 3, 4, 5);
//        sample = new CheckBoxGroup<>("Select options", data);
//    sample.setItemCaptionGenerator(item -> "Option " + item);
//    sample.select(data.get(2), data.get(3));
//    sample.setItemEnabledProvider(item -> item % 2 != 0);
//
//    ...
//
//    sample.addValueChangeListener(event -> Notification.show("Value changed:", String.valueOf(event.getValue()),
//    Type.TRAY_NOTIFICATION));