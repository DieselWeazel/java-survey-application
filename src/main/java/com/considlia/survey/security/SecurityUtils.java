package com.considlia.survey.security;

import com.considlia.survey.ui.HomeView;
import com.considlia.survey.ui.ShowSurveyView;
import com.considlia.survey.ui.UserViews.LoginView;
import com.considlia.survey.ui.UserViews.RegistrationView;
import com.vaadin.flow.server.ServletHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  /** Private Constructor, "singleton" use. */
  private SecurityUtils() {}

  /**
   * Checks if View is allowed access to {@link com.considlia.survey.model.User}
   *
   * @param securedClass being the class to check which roles are allowed.
   * @return if View is allowed access, true if {@link com.considlia.survey.model.User} is allowed,
   *     false if not.
   */
  public static boolean hasAccess(Class securedClass) {
    // TODO might be redundant?
    final boolean allowedViews =
        LoginView.class.equals(securedClass)
            || HomeView.class.equals(securedClass)
            || ShowSurveyView.class.equals(securedClass)
            || RegistrationView.class.equals(securedClass);

    // Allowing views that does not require login
    if (allowedViews) {
      return true;
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Requires authentication
    if (!isUserLoggedIn(authentication)) {
      return false;
    }

    // Allows if no role (admin) is required
    Secured secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
    if (secured == null) {
      return true;
    }

    // Checks roles toward secured annotation.
    List<String> roles = Arrays.asList(secured.value());
    return authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(roles::contains);
  }

  /**
   * Checks if User is logged in via method overloading.
   *
   * @return true if {@link com.considlia.survey.model.User} is logged in, false if not.
   */
  public static boolean isUserLoggedIn() {
    return isUserLoggedIn(SecurityContextHolder.getContext().getAuthentication());
  }

  /**
   * Checks if Logged in User is an instance of AnonymousAuthentication, not to be confused with
   * {@link com.considlia.survey.model.User}. {@link AnonymousAuthenticationToken} being a
   * SpringSecurity authentication.
   *
   * @param authentication authentication to check.
   * @return true if User is not signed in.
   */
  public static boolean isUserLoggedIn(Authentication authentication) {
    return !(authentication instanceof AnonymousAuthenticationToken);
  }

  /** Checks if internal request, for framework purposes. */
  static boolean isFrameworkInternalRequest(HttpServletRequest request) {
    final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
    return parameterValue != null
        && Stream.of(RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
  }
}
