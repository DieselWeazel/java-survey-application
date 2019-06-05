package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.CheckBoxAnswer;
import com.considlia.survey.model.answer.CheckBoxAnswerChoice;
import com.considlia.survey.model.question.MultiQuestion;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.showsurveycomponents.SurveyLoader;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Shows TextQuestions, inherits from the ShowQuestionLayout for base purposes.
 *
 * Written by Jonathan Harr
 */
public class ShowMultiChoiceQuestionLayout extends ShowQuestionLayout {

  private CheckBoxAnswer multiChoiceAnswer;
  private Binder<CheckBoxAnswer> binder;
  private CheckboxGroup<CheckBoxAnswerChoice> checkBoxButtons = new CheckboxGroup<>();
  private List<CheckBoxAnswerChoice> answerAlternativeList = new ArrayList<CheckBoxAnswerChoice>();

  /**
   * Constructs a Layout for Viewing and Storing MultiAnswer(s)
   *
   * @param question MultiQuestion only
   */
  public ShowMultiChoiceQuestionLayout(MultiQuestion question) {
    super(question);
    binder = new Binder<>(CheckBoxAnswer.class);
    multiChoiceAnswer = new CheckBoxAnswer();
    binder.setBean(multiChoiceAnswer);

    question
        .getStringAlternatives()
        .forEach(e -> answerAlternativeList.add(new CheckBoxAnswerChoice(e)));

    checkBoxButtons.setItems(answerAlternativeList);
    add(checkBoxButtons);
    checkBoxButtons.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
  }

  /**
   * Inherited Method, sets the Binder to show a message if fields are empty and only if Question requires an answer.
   */
  public void setMandatoryStatus() {
    if (getQuestion().isMandatory()) {
      binder
          .forField(checkBoxButtons)
          .withValidator(ratioAnswerString -> ratioAnswerString != null && !ratioAnswerString.isEmpty(),
              MANDATORY_QUESTION_MESSAGE)
          .bind(CheckBoxAnswer::getMultiAnswerChoiceSet, CheckBoxAnswer::setMultiAnswerChoiceSet);
    } else {
      binder
          .forField(checkBoxButtons)
          .bind(CheckBoxAnswer::getMultiAnswerChoiceSet, CheckBoxAnswer::setMultiAnswerChoiceSet);
    }
  }

  /**
   * Gathers Response of filled form.
   *
   * @return MultiAnswer
   * @throws ValidationException
   */
  @Override
  public Answer gatherResponse() throws ValidationException {
    multiChoiceAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(multiChoiceAnswer);
    getLOGGER().info("Logging answer: '{}'", multiChoiceAnswer);
    return multiChoiceAnswer;
  }

  /**
   * Checks if CheckBoxButtons are not filled in
   * @param question if question is mandatory
   * @return true if CheckBoxButtons are filled in.
   */
  @Override
  public boolean isCompleted(Question question) {
    getLOGGER().info("ShowMultiChoiceQuestionLayout isCompleted: '{}'", (!checkBoxButtons.isEmpty()));
    return (!checkBoxButtons.isEmpty());
  }
}
