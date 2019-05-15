package com.considlia.survey.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class BaseView extends VerticalLayout {

  public BaseView() {
    setStyle();
  }

  public BaseView(String viewName) {
    H1 title = new H1(viewName);
    title.getStyle().set("margin-top", "20px");
    title.getStyle().set("margin-bottom", "-10px");

    add(title);
    setStyle();
  }

  public void setStyle() {
    getStyle().set("width", "80%");
    getStyle().set("margin", "auto");
  }
}
