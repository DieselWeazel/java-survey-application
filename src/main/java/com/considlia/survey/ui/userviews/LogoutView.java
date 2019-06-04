package com.considlia.survey.ui.userviews;

import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.router.Route;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * View with purpose to redirect User upon logging out.
 */
@Route(value = "logout", layout = MainLayout.class)
public class LogoutView extends BaseView {

  /**
   * Constructor for View
   */
  public LogoutView(HttpServletRequest httpServletRequest) {
    super("Logging out..");
    if (SecurityUtils.isUserLoggedIn()) {
      SecurityContextHolder.clearContext();
      httpServletRequest.getSession(false).invalidate();
      UI.getCurrent().getSession().close();
      UI.getCurrent().getPage().reload();
    }
  }

}
