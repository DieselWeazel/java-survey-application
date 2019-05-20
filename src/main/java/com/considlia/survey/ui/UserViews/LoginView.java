package com.considlia.survey.ui.UserViews;

import com.considlia.survey.model.User;
import com.considlia.survey.repositories.SurveyRepository;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.security.CustomUserService;
import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.HomeView;
import com.considlia.survey.ui.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

/*
Trying to figure out a way to redirect without "UI.getCurrent().navigate(HomeView.class);", looking for a Spring Security integration of sorts.
Jonathan
 */
@Route(value = "login", layout = MainLayout.class)
public class LoginView extends BaseView implements BeforeEnterObserver {

  // -- Login Components --
  private TextField username;
  private PasswordField password;
  private Button submitButton;

  // -- Backend Components --
  @Autowired
  private AuthenticationManager authenticationManagerBean;

  private VerticalLayout loginView;


  public LoginView(
      AuthenticationManager authenticationManagerBean) {
    super("Login");
    this.authenticationManagerBean = authenticationManagerBean;
    username = new TextField();
    password = new PasswordField();
    submitButton = new Button("Login");

    username.setLabel("Username");
    password.setLabel("Password");

    submitButton.addClickListener(
        event -> {
          if (setCurrentUser(username.getValue(), password.getValue())) {
            this.getUI().ifPresent(ui -> ui.navigate(""));
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
      Notification.show("Error, Bad Credentials!");
      return false;
    }
    return true;
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (SecurityUtils.isUserLoggedIn()){
      event.rerouteTo("");
    }
  }
}
