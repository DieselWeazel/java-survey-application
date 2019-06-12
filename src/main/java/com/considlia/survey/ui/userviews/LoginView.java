package com.considlia.survey.ui.userviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.considlia.survey.ui.custom_component.ConfirmDialog;
import com.considlia.survey.ui.custom_component.ConfirmDialog.ConfirmDialogBuilder;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

/**
 * Class that handles the login process. If User is logged in and somehow arrives to this view, the
 * BeforeEnterObserver throws them back.
 */
@Route(value = "login", layout = MainLayout.class)
public class LoginView extends BaseView implements BeforeEnterObserver {

  private TextField username;
  private PasswordField password;
  @Autowired
  private AuthenticationManager authenticationManagerBean;

  /** LoginView Constructor, for signing in. Does not open if user is logged in. */
  public LoginView() {
    super("Login");

    username = new TextField();
    username.focus();
    username.addKeyPressListener(Key.ENTER, event -> logInEvent());

    password = new PasswordField();
    password.addKeyPressListener(Key.ENTER, event -> logInEvent());

    Button submitButton = new Button("Login");
    Button registerButton = new Button("Register");

    username.setLabel("Username");
    password.setLabel("Password");

    submitButton.addClickListener(event -> logInEvent());

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
   * Get the values from the username and password {@link TextField}s. If they aren't valid a
   * {@link ConfirmDialogBuilder} is showed
   */
  public void logInEvent() {
    if (setCurrentUser(username.getValue(), password.getValue())) {
      UI.getCurrent().getSession().close();
      UI.getCurrent().getPage().reload();
    } else {
      ConfirmDialog confirmDialog = new ConfirmDialogBuilder<RegistrationView>().with($ -> {
        $.addHeaderText("Bad Credentials!");
        $.addContentText("Wrong Username or Password, try again!");
        $.addSimpleCloseButton("Ok");
      }).createConfirmDialog();
      confirmDialog.open();
    }
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
