package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.RatioAnswer;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.ui.custom_component.showsurveycomponents.SurveyLoader;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Shows TextQuestions, inherits from the ShowQuestionLayout for base purposes.
 *
 * Written by Jonathan Harr
 */
public class ShowRatioQuestionLayout extends ShowQuestionLayout {

  private RatioAnswer ratioAnswer = new RatioAnswer();
  private Binder<RatioAnswer> binder = new Binder<>(RatioAnswer.class);
  private RadioButtonGroup<String> ratioRadioButtons = new RadioButtonGroup<>();

  /**
   * Constructs a Layout for Viewing and Storing RatioAnswer
   *
   * @param question RatioQuestion
   */
  public ShowRatioQuestionLayout(RatioQuestion question) {
    super(question);
    List<String> options = new ArrayList<>();
    binder.setBean(ratioAnswer);
    for (int i = 1; i <= question.getChoices(); i++) {
      if (i == 1) {
        options.add(i + " " + question.getStart());
      } else if (i == question.getChoices()) {
        options.add(i + " " + question.getEnd());
      } else {
        options.add(Integer.toString(i));
      }
    }

    binder
        .forField(ratioRadioButtons)
        .bind(RatioAnswer::getRatioAnswer, RatioAnswer::setRatioAnswer);

    ratioRadioButtons.setItems(options);
    ratioRadioButtons.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
    add(ratioRadioButtons);
  }

  /**
   * Inherited Method, sets the Binder to show a message if fields are empty and only if Question requires an answer.
   */
  public void setMandatoryStatus() {
    if (getQuestion().isMandatory()) {
      binder
          .forField(ratioRadioButtons)
          .withValidator(ratioAnswerString -> ratioAnswerString != null && !ratioAnswerString.isEmpty(),
              MANDATORY_QUESTION_MESSAGE)
          .bind(RatioAnswer::getRatioAnswer, RatioAnswer::setRatioAnswer);
    } else {
      binder
          .forField(ratioRadioButtons)
          .bind(RatioAnswer::getRatioAnswer, RatioAnswer::setRatioAnswer);
    }
  }

  /**
   * Gathers Response of filled form.
   *
   * @return RatioAnswer
   * @throws ValidationException
   */
  @Override
  public Answer gatherResponse() throws ValidationException {
    ratioAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    binder.writeBean(ratioAnswer);
    getLOGGER().info("Logging answer: '{}'", ratioAnswer);
    return ratioAnswer;
  }
  /**
   * Checks if RatioRadioButtons are empty
   * @param question if question is mandatory
   * @return true if RatioRadioButtons are filled in.
   */
  public boolean isCompleted(Question question) {
    getLOGGER().info("ShowMultiChoiceQuestionLayout isCompleted: '{}'", (!ratioRadioButtons.isEmpty()));
    return (!ratioRadioButtons.isEmpty());
  }
}
