package com.considlia.survey.ui.UserViews;

import com.considlia.survey.model.Survey;
import com.considlia.survey.model.User;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.HomeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
/*
Trying to figure out a way to redirect without "UI.getCurrent().navigate(HomeView.class);", looking for a Spring Security integration of sorts.
Jonathan
 */
@Route(value = "login")
public class LoginView extends BaseView {

  // -- Login Components --
  private TextField username;
  private PasswordField password;
  private Button submitButton;

  // -- Backend Components --
  @Autowired private AuthenticationManager authenticationManagerBean;

  private VerticalLayout loginView;

  private SurveyRepository surveyRepository;
  private UserRepository userRepository;

  public LoginView(
      AuthenticationManager authenticationManagerBean,
      SurveyRepository surveyRepository,
      UserRepository userRepository) {
    super("Login");
    this.authenticationManagerBean = authenticationManagerBean;
    this.surveyRepository = surveyRepository;
    this.userRepository = userRepository;
    username = new TextField();
    password = new PasswordField();
    submitButton = new Button("Login");

    username.setLabel("Username");
    password.setLabel("Password");

    submitButton.addClickListener(
        event -> {
          if (setCurrentUser(username.getValue(), password.getValue())) {
            UI.getCurrent().navigate(HomeView.class);
          }
        });

    username.setWidth("380px");
    password.setWidth("380px");
    loginView = new VerticalLayout(username, password, submitButton);
    add(loginView);

    loginView.setWidth("400px");
    loginView.setAlignItems(Alignment.CENTER);
    setAlignItems(Alignment.CENTER);
  }


  public boolean setCurrentUser(String login, String password) {
    try {
      Authentication request = new UsernamePasswordAuthenticationToken(login, password);
      Authentication result = authenticationManagerBean.authenticate(request);
      SecurityContextHolder.getContext().setAuthentication(result);
    } catch (BadCredentialsException e) {
      return false;
    }
    return true;
  }
}
