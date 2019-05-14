package com.considlia.survey.ui.custom_component;

public enum QuestionType {
  TEXTFIELD("Text question"), RADIO("Radio question"), CHECKBOX("Checkbox question"), RATIO(
      "Ratio question");

  private String text;

  QuestionType(final String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return text;
  }
}
