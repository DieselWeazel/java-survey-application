package com.considlia.survey.ui.UserViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "loginprocess")
public class ProcessingView extends VerticalLayout {

  public ProcessingView() {
    add(new H1("You are being logged in.."));
  }
}
