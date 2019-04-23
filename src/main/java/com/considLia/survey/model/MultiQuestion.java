package com.considLia.survey.model;

import java.util.ArrayList;
import java.util.List;

public class MultiQuestion extends Question {

  private List<String> alternativeList;
  private int questionType;

  public MultiQuestion() {}

  public MultiQuestion(String questionTitle, int questionType) {
    super(questionTitle);
    alternativeList = new ArrayList<>();
    setQuestionType(questionType);
  }

  public List<String> getAlternativeList() {
    return alternativeList;
  }

  public void setAlternativeList(List<String> alternativeList) {
    this.alternativeList = alternativeList;
  }

  public int getQuestionType() {
    return questionType;
  }

  public void setQuestionType(int questionType) {
    this.questionType = questionType;
  }

  @Override
  public String toString() {
    return "MultiQuestion [alternativeList=" + alternativeList + ", questionType=" + questionType
        + "]";
  }

}
