package com.considlia.survey.ui.userviews;

import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.router.Route;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "logout", layout = MainLayout.class)
public class LogoutView extends BaseView {

  /**
   * Constructor for View
   */
  public LogoutView() {
    super("You have been logged out");
    add(new H5("See you around!"));
  }
}
