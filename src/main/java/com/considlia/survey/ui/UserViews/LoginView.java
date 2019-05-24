package com.considlia.survey.ui.UserViews;

import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
  private Button registerButton;

  private HorizontalLayout horizontalLayout;

  // -- Backend Components --
  @Autowired
  private AuthenticationManager authenticationManagerBean;

  private VerticalLayout loginView;


  public LoginView() {
    super("Login");
    username = new TextField();
    password = new PasswordField();
    submitButton = new Button("Login");
    this.registerButton = new Button("Register");
    H4 h4 = new H4("Don't have an account?");

    username.setLabel("Username");
    password.setLabel("Password");

    submitButton.addClickListener(
        event -> {
          if (setCurrentUser(username.getValue(), password.getValue())) {
            UI.getCurrent().getSession().close();
            UI.getCurrent().getPage().reload();
          }
        });

    username.setWidth("380px");
    password.setWidth("380px");
    loginView = new VerticalLayout(username, password, submitButton);
    add(loginView);

    // If User isn't registered, added a small little navigation choice to the register menu
    registerButton.addClickListener(e -> UI.getCurrent().navigate("registration"));
    this.horizontalLayout = new HorizontalLayout(h4, registerButton);
    add(horizontalLayout);
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
    if (SecurityUtils.isUserLoggedIn()) {
      event.rerouteTo("");
    }
  }
}
