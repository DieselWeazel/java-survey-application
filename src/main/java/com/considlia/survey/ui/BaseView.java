package com.considlia.survey.ui;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@StyleSheet("css/app.css")
public abstract class BaseView extends VerticalLayout {

  public BaseView() {
    setClassId();
  }

  public BaseView(String viewName) {
    setClassId();

    H1 title = new H1(viewName);
    title.setClassName("title");

    add(title);
  }

  public void setClassId() {
    setSizeUndefined();
    setId("baseview");
  }

}
