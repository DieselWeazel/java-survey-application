package com.considlia.survey.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

public class CustomRequestCache extends HttpSessionRequestCache {

  /**
   * Stores the previously visited site and redirects after login.
   *
   * @param request User Requested page.
   * @param response Response from Server.
   */
  @Override
  public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
    if (!SecurityUtils.isFrameworkInternalRequest(request)) {
      super.saveRequest(request, response);
    }
  }
}
