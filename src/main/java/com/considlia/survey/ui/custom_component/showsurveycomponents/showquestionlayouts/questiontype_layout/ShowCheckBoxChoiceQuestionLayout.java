package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout;

import java.util.ArrayList;
import java.util.List;
import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.CheckBoxAnswer;
import com.considlia.survey.model.answer.CheckBoxAnswerChoice;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

/**
 * Shows CheckBoxsQuestion, inherits from the ShowQuestionLayout for base purposes.
 *
 * Written by Jonathan Harr
 */
public class ShowCheckBoxChoiceQuestionLayout extends ShowQuestionLayout {

  private CheckBoxAnswer checkBoxAnswer;
  private Binder<CheckBoxAnswer> binder;
  private CheckboxGroup<CheckBoxAnswerChoice> checkBoxButtons = new CheckboxGroup<>();
  private List<CheckBoxAnswerChoice> answerAlternativeList = new ArrayList<CheckBoxAnswerChoice>();

  /**
   * Constructs a Layout for Viewing and Storing CheckBox(s)
   *
   * @param question MultiQuestion only
   */
  public ShowCheckBoxChoiceQuestionLayout(MultiQuestion question) {
    super(question);
    binder = new Binder<>(CheckBoxAnswer.class);
    checkBoxAnswer = new CheckBoxAnswer();
    binder.setBean(checkBoxAnswer);

    question.getStringAlternatives()
        .forEach(e -> answerAlternativeList.add(new CheckBoxAnswerChoice(e)));

    checkBoxButtons.setItems(answerAlternativeList);
    // binder
    // .forField(checkBoxButtons)
    // .bind(MultiAnswer::getMultiAnswerChoiceSet, MultiAnswer::setMultiAnswerChoiceSet);

    add(checkBoxButtons);
    checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
  }

  /**
   * Inherited Method, sets the Binder to show a message if fields are empty and only if Question
   * requires an answer.
   */
  public void setMandatoryStatus() {
    if (getQuestion().isMandatory()) {
      binder.forField(checkBoxButtons)
          .withValidator(
              ratioAnswerString -> ratioAnswerString != null && !ratioAnswerString.isEmpty(),
              mandatoryQuestionMessage)
          .bind(CheckBoxAnswer::getMultiAnswerChoiceSet, CheckBoxAnswer::setMultiAnswerChoiceSet);
    } else {
      binder.forField(checkBoxButtons).bind(CheckBoxAnswer::getMultiAnswerChoiceSet,
          CheckBoxAnswer::setMultiAnswerChoiceSet);
    }
  }

  /**
   * Gathers Response of filled form.
   *
   * @return checkBoxAnswer
   * @throws ValidationException
   */
  @Override
  public Answer gatherResponse() throws ValidationException {
    checkBoxAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(checkBoxAnswer);
    getLOGGER().info("Logging answer: '{}'", checkBoxAnswer);
    return checkBoxAnswer;
  }

  /**
   * Checks if CheckBoxButtons are not filled in
   * 
   * @param question if question is mandatory
   * @return true if CheckBoxButtons are filled in.
   */
  @Override
  public boolean isCompleted(Question question) {
    getLOGGER().info("ShowMultiChoiceQuestionLayout isCompleted: '{}'",
        (!checkBoxButtons.isEmpty()));
    return (!checkBoxButtons.isEmpty());
  }
}
