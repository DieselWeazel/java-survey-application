package com.considlia.survey.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@StyleSheet("css/app.css")
public abstract class BaseView extends VerticalLayout {

  private final H1 title;

  /**
   * Constructs an empty BaseView.
   */
  public BaseView() {
    setClassId();

    setClassId();

    title = new H1();
    title.setClassName("title");
    title.setVisible(false);
    add(title);
  }

  /**
   * Constructs a baseView containing a {@link H1}
   * 
   * @param viewName the H1 text
   */
  public BaseView(String viewName) {
    this();
    setTitle(viewName);
  }

  /**
   * Sets the title of the view.
   * @param titleText Title of view.
   */
  protected void setTitle(String titleText) {
    title.setText(titleText);
    title.setVisible(true);
  }

  /**
   * Undefined size so it can be changed with CSS and sets id
   */
  public void setClassId() {
    setSizeUndefined();
    setId("baseview");
  }

  /**
   * Navigate to the Custom Success View, see {@link ConfirmSuccessView}
   * @param s being of a supported String within {@link ConfirmSuccessView}
   */
  public void navigateToSuccessView(String s){
    UI.getCurrent().navigate("confirm/" + s);
  }

  /**
   * Navigates back to home/HomeView.class {@link HomeView}
   */
  public void navigateBackToHomeView() {
    UI.getCurrent().navigate("");
  }
}
