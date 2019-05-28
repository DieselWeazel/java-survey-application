package com.considlia.survey.ui.userviews;

import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.considlia.survey.ui.custom_component.ConfirmDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
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
Currently needs a refactoring/correctly
processed way of checking for Username and Password being correct. (throws exception and acts on it as of onw)
Jonathan
 */
@Route(value = "login", layout = MainLayout.class)
public class LoginView extends BaseView implements BeforeEnterObserver {

  @Autowired private AuthenticationManager authenticationManagerBean;

  /** LoginView Constructor, for signing in. Does not open if user is logged in. */
  public LoginView() {
    super("Login");
    TextField username = new TextField();
    PasswordField password = new PasswordField();
    Button submitButton = new Button("Login");
    Button registerButton = new Button("Register");

    username.setLabel("Username");
    password.setLabel("Password");

    submitButton.addClickListener(
        event -> {
          if (setCurrentUser(username.getValue(), password.getValue())) {
            UI.getCurrent().getSession().close();
            UI.getCurrent().getPage().reload();
          } else {
            ConfirmDialog confirmDialog = new ConfirmDialog();
            confirmDialog.open();
          }
        });

    username.setWidth("380px");
    password.setWidth("380px");
    VerticalLayout loginView = new VerticalLayout(username, password, submitButton);
    add(loginView);

    // If User isn't registered, added a small little navigation choice to the register menu
    registerButton.addClickListener(e -> UI.getCurrent().navigate("registration"));
    add(new VerticalLayout(new H4("Don't have an account?"), registerButton));

    loginView.setWidth("400px");
    loginView.setAlignItems(Alignment.CENTER);
    setAlignItems(Alignment.CENTER);
  }

  /**
   * Sets current {@link com.considlia.survey.model.User} if username and password is correct.
   *
   * @param userName being username.
   * @param password being password.
   * @return true if username and password is correct, false if not.
   */
  public boolean setCurrentUser(String userName, String password) {
    try {
      Authentication request = new UsernamePasswordAuthenticationToken(userName, password);
      Authentication result = authenticationManagerBean.authenticate(request);
      SecurityContextHolder.getContext().setAuthentication(result);
    } catch (BadCredentialsException e) {
      return false;
    }
    return true;
  }

  /**
   * If User is signed in, redirects user back to previous URL.
   *
   * @param event the accessed URL before class construction.
   */
  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (SecurityUtils.isUserLoggedIn()) {
      event.rerouteTo("");
    }
  }
}
