package com.considlia.survey.ui.custom_component.question_with_button;

import java.util.ArrayList;
import java.util.List;
import com.considlia.survey.model.QuestionType;
import com.considlia.survey.model.question.RatioQuestion;
import com.considlia.survey.ui.CreateSurveyView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;

/**
 * Class extending {@link QuestionWithButtons}. Contains the specific {@link Component}s for
 * {@link RatioQuestion}
 *
 */
public class RatioQuestionWithButtons extends QuestionWithButtons {

  private RatioQuestion ratioQuestion;
  private String start, end;
  private int choices;
  private RadioButtonGroup<String> radioOptions;
  private List<String> options = new ArrayList<>();

  /**
   * Calls the super constructor. Also adds a {@link RadioButtonGroup} from the values off
   * {@link String} start, end and {@link int} choices from the question parameters
   *
   * @param question get {@link QuestionType} and also for passing it to super
   * @param survey for passing it to super
   */
  public RatioQuestionWithButtons(RatioQuestion question, CreateSurveyView survey) {
    super(question, survey);

    this.ratioQuestion = question;

    setStart(question.getStart());
    setEnd(question.getEnd());
    setChoices(question.getChoices());

    populateList();

    radioOptions = new RadioButtonGroup<>();
    radioOptions.setItems(options);
    radioOptions.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
    add(radioOptions);
  }

  /**
   * Populates the option-list. The length of the list depends from the value of choices. Adds the
   * String value of start to the first element in the list. Adds the String value of end to the
   * last element in the list.
   */
  public void populateList() {
    for (int i = 1; i <= choices; i++) {
      if (i == 1) {
        options.add(Integer.toString(i) + " " + start);
      } else if (i == choices) {
        options.add(Integer.toString(i) + " " + end);
      } else {
        options.add(Integer.toString(i));
      }
    }
  }

  /**
   * Updates the options list and replaces the current {@link RadioButtonGroup} with a new, updated,
   * one
   */
  public void updateRadioOptions() {
    options.clear();

    populateList();

    RadioButtonGroup<String> newRadio = new RadioButtonGroup<>();
    newRadio.setItems(options);
    newRadio.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

    replace(radioOptions, newRadio);
    radioOptions = newRadio;

  }

  /**
   * Return the value of {@link String} start
   *
   * @return String value {@link start}
   */
  public String getStart() {
    return start;
  }

  /**
   * Set the value of {@link String} start. Also set {@link RatioQuestion#setStart(String)}
   *
   * @param start new {@link String} value
   */
  public void setStart(String start) {
    getQuestion().setStart(start);
    this.start = start;
  }

  /**
   * Return the value of {@link String} end
   *
   * @return String value {@link end}
   */
  public String getEnd() {
    return end;
  }

  /**
   * Set the value of {@link String} end. Also set {@link RatioQuestion#setEnd(String)}
   *
   * @param end new {@link String} value
   */
  public void setEnd(String end) {
    getQuestion().setEnd(end);
    this.end = end;
  }

  /**
   * Return the value of {@link int} choices
   *
   * @return int value {@link choices}
   */
  public int getChoices() {
    return choices;
  }

  /**
   * Set the value of {@link int} choices. Also set {@link RatioQuestion#setChoices(int)}
   *
   * @param choices new {@link int} value
   */
  public void setChoices(int choices) {
    getQuestion().setChoices(choices);
    this.choices = choices;
  }

  /**
   * Return the question of {@link RatioQuestionWithButtons}
   *
   * @return ratioQuestion {@link RatioQuestion}
   */
  @Override
  public RatioQuestion getQuestion() {
    return ratioQuestion;
  }

}
