package com.considlia.survey.ui;

import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.custom_component.SurveyGrid;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "", layout = MainLayout.class)
public class HomeView extends BaseView {

  private SurveyGrid surveyGrid;

  private SurveyRepository surveyRepository;

  private Button loginButton;
  private Button registerButton;
  private HorizontalLayout horizontalLayout;

  //  @Autowired
  //  private AuthenticationManager authenticationManagerBean;

  public HomeView(
      SurveyRepository surveyRepository, AuthenticationManager authenticationManagerBean) {
    super("Home");
    this.surveyRepository = surveyRepository;
    this.loginButton = new Button("Login");
    this.registerButton = new Button("Register");
    //    this.authenticationManagerBean = authenticationManagerBean;
    Authentication request = new UsernamePasswordAuthenticationToken("admin", "admin");
    Authentication result = authenticationManagerBean.authenticate(request);
    SecurityContextHolder.getContext().setAuthentication(result);
    loginButton.addClickListener(e -> UI.getCurrent().navigate("login"));
    registerButton.addClickListener(e -> UI.getCurrent().navigate("registration"));
    this.horizontalLayout = new HorizontalLayout(loginButton, registerButton);
    if (!SecurityUtils.isUserLoggedIn()) {
      add(horizontalLayout);
    }

    surveyGrid = new SurveyGrid(this.getClass(), surveyRepository);
    add(surveyGrid);
  }
}
