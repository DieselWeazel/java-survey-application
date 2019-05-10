package com.considlia.survey.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class BaseView extends VerticalLayout {

  public BaseView() {
    setStyle();
  }

  public BaseView(String viewName) {
    add(new H1(viewName));
    setStyle();
  }

  public void setStyle() {
    getStyle().set("width", "80%");
    getStyle().set("margin", "auto");
  }

}
