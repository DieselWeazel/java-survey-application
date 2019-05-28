package com.considlia.survey.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@StyleSheet("css/app.css")
public abstract class BaseView extends VerticalLayout {

  /**
   * Constructs an empty BaseView.
   */
  public BaseView() {
    setClassId();
  }

  /**
   * Constructs a baseView containing a {@link H1}
   * 
   * @param viewName the H1 text
   */
  public BaseView(String viewName) {
    setClassId();

    H1 title = new H1(viewName);
    title.setClassName("title");

    add(title);
  }

  /**
   * Undefined size so it can be changed with CSS and sets id
   */
  public void setClassId() {
    setSizeUndefined();
    setId("baseview");
  }

  /**
   * Navigates back to home/HomeView.class
   * {@link HomeView}
   */
  public void navigateBackToHomeView(){
    UI.getCurrent().navigate("");
  }
}
