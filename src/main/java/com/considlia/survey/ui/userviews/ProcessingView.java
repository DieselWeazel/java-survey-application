package com.considlia.survey.ui.userviews;

import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "logoutprocess", layout = MainLayout.class)
public class ProcessingView extends BaseView implements BeforeEnterObserver {

  private HttpServletRequest httpServletRequest;

  /**
   * Constructor for View when processing, inbetween logins and being successfuly authenticated.
   */
  public ProcessingView(HttpServletRequest httpServletRequest) {
    add(new H1("Processing.."));
    this.httpServletRequest = httpServletRequest;
    logoutUser();
  }

  /**
   * Logs the user out. Reloads the page.
   */
  public void logoutUser() {
    SecurityContextHolder.clearContext();
    httpServletRequest.getSession(false).invalidate();
//    UI.getCurrent().getSession().close();
//    UI.getCurrent().getPage().reload();
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (!SecurityUtils.isUserLoggedIn()) {
      event.rerouteTo("logout");
    }
  }
}
