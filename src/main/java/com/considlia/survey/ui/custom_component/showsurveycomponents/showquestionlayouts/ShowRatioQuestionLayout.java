package com.considlia.survey.ui.custom_component.showsurveycomponents.showquestionlayouts;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.RatioAnswer;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.ui.custom_component.showsurveycomponents.ShowQuestionComponent;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class ShowRatioQuestionLayout extends ShowQuestionLayout implements ShowQuestionComponent {

  private RatioAnswer ratioAnswer = new RatioAnswer();
  private Binder<RatioAnswer> binder = new Binder<>(RatioAnswer.class);

  /**
   * Constructs a Layout for Viewing and Storing RatioAnswer
   *
   * @param question RatioQuestion
   */
  public ShowRatioQuestionLayout(RatioQuestion question) {
    super(question);
    RadioButtonGroup<String> ratioRadioButtons = new RadioButtonGroup<>();
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
}
