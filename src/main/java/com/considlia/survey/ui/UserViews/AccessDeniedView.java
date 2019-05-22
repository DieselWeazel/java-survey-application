package com.considlia.survey.ui.UserViews;

import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "accessdenied", layout = MainLayout.class)
public class AccessDeniedView extends BaseView {

  private Button loginButton;
  private Button registerButton;
  private Button backToHomeButton;

  private HorizontalLayout horizontalLayout;

  public AccessDeniedView() {
    super("Oops!");
    add(new H3("Looks like you tried to access a forbidden route."));
    add(new H4("Have you tried logging in before accessing this destination?"));

    this.loginButton = new Button("Login");
    this.registerButton = new Button("Register");
    this.backToHomeButton = new Button("Go Home");

    loginButton.addClickListener(e -> UI.getCurrent().navigate("login"));
    registerButton.addClickListener(e -> UI.getCurrent().navigate("registration"));
    backToHomeButton.addClickListener(e -> UI.getCurrent().navigate(""));

    this.horizontalLayout = new HorizontalLayout(loginButton, registerButton, backToHomeButton);
    add(horizontalLayout);
  }
}
