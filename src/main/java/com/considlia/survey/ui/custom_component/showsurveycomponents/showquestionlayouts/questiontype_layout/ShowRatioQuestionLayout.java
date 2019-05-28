package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.RatioAnswer;
import com.considlia.survey.model.answer.TextAnswer;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.ui.custom_component.showsurveycomponents.ShowQuestionComponent;
import com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts.ShowQuestionLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.StringLengthValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ShowRatioQuestionLayout extends ShowQuestionLayout
    implements ShowQuestionComponent {

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
        options.add(Integer.toString(i) + " " + question.getStart());
      } else if (i == question.getChoices()) {
        options.add(Integer.toString(i) + " " + question.getEnd());
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

  @Override
  public void setMandatoryStatus() {
    if (getQuestion().isMandatory()) {
      binder
          .forField(ratioRadioButtons)
          .withValidator(ratioAnswerString -> ratioAnswerString != null && !ratioAnswerString.isEmpty(), mandatoryQuestionMessage)
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
  public Answer gatherResponse(Consumer<Answer> consumer) throws ValidationException {
    ratioAnswer.setQuestion(getQuestion());
    getLOGGER().info("Logging question: '{}'", getQuestion());
    getLOGGER().info("Bean Valid: '{}'", binder.writeBeanIfValid(ratioAnswer));
    if (binder.writeBeanIfValid(ratioAnswer)){
      getLOGGER().info("Logging answer: '{}'", ratioAnswer);
      return ratioAnswer;
    } else {
      consumer.accept(ratioAnswer);
    }
    return null;
  }
}
