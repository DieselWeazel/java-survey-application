package com.considlia.survey.ui.custom_component;

public enum QuestionType {
  TEXT("Text question"),
  RADIO("Radio question"),
  CHECKBOX("Checkbox question");

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
