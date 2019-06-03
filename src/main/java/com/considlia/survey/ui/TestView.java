package com.considlia.survey.ui;

import com.considlia.survey.model.Survey;
import com.considlia.survey.repositories.ResponseRepository;
import com.considlia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "testview", layout = MainLayout.class)
public class TestView extends BaseView implements HasUrlParameter<Long> {

  // -- Private Variables --
  // -- Containers --i
  //TODO maybe these could be moved to baseview? or another view, which extends baseview.
  //TODO both of them are the same as in ShowSurveyView
  private VerticalLayout headerVerticalLayout = new VerticalLayout();
  private VerticalLayout surveyVerticalLayout = new VerticalLayout();

  private Button navigateButton;

  private H1 h1;

  private Survey survey;
  private ResponseRepository responseRepository;
  private SurveyRepository surveyRepository;

  public TestView(ResponseRepository responseRepository, SurveyRepository surveyRepository){
    this.responseRepository = responseRepository;
    this.surveyRepository = surveyRepository;

    h1 = new H1("PlaceHolder // Survey Not Actually Found, Text not Updated");
    navigateButton = new Button("GO Home");
  }

  private void initUI(){
    headerVerticalLayout.setId("createheader");
    surveyVerticalLayout.setId("questionpackage");
    h1.setMinWidth("70%");

    headerVerticalLayout.add(h1);

    add(navigateButton);
  }

  @Override
  public void setParameter(BeforeEvent event, Long parameter) {
    if (parameter != null){
      if (surveyRepository.findById(parameter).isPresent()){
        survey = surveyRepository.getSurveyById(parameter);
      } else {
        add(new H5("ERROR, no Survey by the ID of: " + parameter + " exists."));
        goHome();
      }
    }
  }

  /**
   * If no survey is loaded, navigateButton changes function, offers a way back to homeview for User.
   * TODO duplicate code from ShowSurveyView
   */
  public void goHome() {
    navigateButton.setText("Go To Mainview");
    navigateButton.addClickListener(e -> navigateBackToHomeView());
    add(navigateButton);
  }
}
