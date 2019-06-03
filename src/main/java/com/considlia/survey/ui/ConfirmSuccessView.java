package com.considlia.survey.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

/**
 * Custom Navigation Page, main purpose is to display to user what has happened upon a successful event such as saving a survey
 * or sending in a response. Can be modified to include more purposes.
 *
 * Written By Jonathan
 */
@Route(value = "confirm", layout = MainLayout.class)
public class ConfirmSuccessView extends BaseView implements HasUrlParameter<String> {

  /**
   * Custom Strings for Navigating to this View, add new Strings depending on the purpose of the arrival to this view.
   */
  public static String SURVEY_RESPONDED_STRING = "responded";
  public static String SURVEY_CREATED_STRING = "created";

  private H5 informationText;

  private Button goToProfileButton = new Button("My Profile", e -> UI.getCurrent().navigate("profileview"));
  private Button goHomeButton = new Button("Back to Home", e-> navigateBackToHomeView());

  /**
   * Constructor for View.
   */
  public ConfirmSuccessView(){
    super("Done!");
    informationText = new H5();
  }

  /**
   * Parameter is mainly used for the purpose of the page. The only supported parameters are within this class.
   * @param event being the navigation event.
   * @param parameter being the arrival purpose. Creation of Survey, responded survey, etc.
   */
  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    if (!parameter.isEmpty()){
      if (parameter.equals(SURVEY_RESPONDED_STRING)){
        informationText.setText("Your Response has been saved, thank you for participating!");
        add(informationText, goHomeButton);
      } else if (parameter.equals(SURVEY_CREATED_STRING)){
        informationText.setText("Your Survey has been created. You can find it under your profile. Where do you want to go now?");
        add(informationText);
        add(new HorizontalLayout(goHomeButton, goToProfileButton));
      }
    } else {
      informationText.setText("Error, this wasn't supposed to happen!");
      add(informationText, goHomeButton);
    }
  }
}
