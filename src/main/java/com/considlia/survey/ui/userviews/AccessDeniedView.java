package com.considlia.survey.ui.userviews;

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


  /**
   * Constructor for View when reaching a
   */
  public AccessDeniedView() {
    super("Oops!");
    add(new H3("Looks like you tried to access a forbidden route."));
    add(new H4("Have you tried logging in before accessing this destination?"));

    add(new HorizontalLayout(new Button("Login", e -> UI.getCurrent().navigate("login")),
        new Button("Register", e -> UI.getCurrent().navigate("registration")),
        new Button("Go Home", e -> UI.getCurrent().navigate(""))));
  }
}
