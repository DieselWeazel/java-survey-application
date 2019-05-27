package com.considlia.survey.ui.userviews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "loginprocess")
public class ProcessingView extends VerticalLayout {

  /**
   * Constructor for View when processing, inbetween logins and being successfuly authenticated.
   */
  public ProcessingView() {
    add(new H1("You are being logged in.."));
  }
}
