package com.considlia.survey.ui.UserViews;

import com.considlia.survey.model.Role;
import com.considlia.survey.model.Survey;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.security.CustomUserService;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.considlia.survey.ui.custom_component.SurveyGrid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "profileview", layout = MainLayout.class)
@Secured({Role.USER, Role.ADMIN})
public class MyProfileView extends BaseView {

  private SurveyGrid surveyGrid;

  private SurveyRepository surveyRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CustomUserService customUserService;

  public MyProfileView(
      SurveyRepository surveyRepository,
      UserRepository userRepository,
      CustomUserService customUserService) {
    super("My Profile");
    this.userRepository = userRepository;
    this.customUserService = customUserService;

    /*
    does not show username as of now?
     */
    add(new H3(customUserService.getUsername()));
    List<Survey> userSurveyList = new ArrayList<>();
    userSurveyList = surveyRepository.findAllByUserId(customUserService.getUser().getId());

    this.surveyRepository = surveyRepository;
    surveyGrid = new SurveyGrid(this.getClass(), surveyRepository, userSurveyList);
    add(surveyGrid);
  }
}
