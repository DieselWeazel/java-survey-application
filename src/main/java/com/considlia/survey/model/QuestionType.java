package com.considlia.survey.model;

/**
 * Class to set the Type of Question The Question can be Textfield, Radio, Checkbox, Ratio,
 * Textarea.
 */
public enum QuestionType {
  TEXTFIELD("Text question"), RADIO("Radio question"), CHECKBOX("Checkbox question"), RATIO(
      "Ratio question"), TEXTAREA("Text area");

  private String text;

  /**
   * Constructor for QuestionType
   * 
   * @param text - sends in the QuestionType in text
   */
  QuestionType(final String text) {
    this.text = text;
  }

  /**
   * Gets the questionType in text
   * 
   * @return The QuestionType in text
   */
  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return text;
  }
}
