package com.considlia.survey.ui;

import com.considlia.survey.repositories.ResponseRepository;
import com.considlia.survey.repositories.SurveyRepository;
import com.vaadin.flow.router.Route;

/**
 * This class extends ShowSurvey, the difference is that the "saveButton" is not shown. Making it
 * impossible for the user to answer the survey
 */
@Route(value = "previewsurvey", layout = MainLayout.class)
public class PreviewSurvey extends ShowSurveyView {

  /**
   * Constructor uses parameters from superclass. Sets saveButton to not visible
   * 
   * @param surveyRepository
   * @param responseRepository
   */
  public PreviewSurvey(SurveyRepository surveyRepository, ResponseRepository responseRepository) {
    super(surveyRepository, responseRepository);
    saveButton.setVisible(false);
    isPreviewMode = true;
    containsMandatory = false;
  }
}
