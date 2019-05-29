package com.considlia.survey.ui.custom_component.showsurveycomponents;

import java.util.List;

public interface EntityCommand<T> {

  List<T> getSurveyComponentList();
  void gatherAnswers();

}
