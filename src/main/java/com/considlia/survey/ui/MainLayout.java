package com.considlia.survey.ui;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.considlia.survey.security.CustomUserService;
import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.userviews.AccessDeniedView;
import com.considlia.survey.ui.userviews.LoginView;
import com.considlia.survey.ui.userviews.LogoutView;
import com.considlia.survey.ui.userviews.MyProfileView;
import com.considlia.survey.ui.userviews.RegistrationView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@StyleSheet("css/app.css")
public class MainLayout extends VerticalLayout implements RouterLayout {

  private Map<Class<? extends Component>, RouterLink> targets = new HashMap<>();
  private Map<String, Class<? extends Component>> targetPaths = new HashMap<>();

  private HorizontalLayout navigation;
  private VerticalLayout contentContainer;
  @Autowired
  private CustomUserService customUserService;

  /**
   * Manages header for site. Adds different buttons depending on if user is logged in.
   */
  public MainLayout(CustomUserService customUserService) {
    this.customUserService = customUserService;
    setId("mainlayout");

    navigation = new HorizontalLayout();
    navigation.add(createRouterLink(HomeView.class, "Home", VaadinIcon.HOME));

    if (SecurityUtils.isUserLoggedIn()) {
      navigation.add(
          createRouterLink(CreateSurveyView.class, "Create New Survey", VaadinIcon.PLUS_CIRCLE));
      navigation.add(createRouterLink(MyProfileView.class, "Profile View", VaadinIcon.USER));

      H4 usernameHeader = new H4();
      usernameHeader.setText("Logged in as: " + customUserService.getUser().getUsername());
      navigation.add(usernameHeader);
      usernameHeader.getStyle().set("margin-left", "auto");
      usernameHeader.getStyle().set("margin-top", "auto");
      usernameHeader.getStyle().set("color", "#3966d8");

      Button logoutButton = createRouterLink(LogoutView.class, "Logout", VaadinIcon.EXIT);
      navigation.add(logoutButton);

    } else {

      navigation.add(createRouterLink(RegistrationView.class, "Registration", VaadinIcon.PENCIL));
      Button loginButton = createRouterLink(LoginView.class, "Login", VaadinIcon.SIGN_IN);
      navigation.add(loginButton);
      loginButton.getStyle().set("margin-left", "auto");

    }
    navigation.setClassName("header");
    contentContainer = new VerticalLayout();
    contentContainer.setClassName("content");

    add(navigation, contentContainer);
  }

  /**
   * Creates a {@link Button} with a link to a new view. Checks {@link SecurityUtils} before sending
   * user to targetViewClass
   * 
   * @param targetViewClass, the class the Button should navigate to
   * @param text, text shown to user on Button
   * @param icon
   * @returns new {@link Button}
   */
  private Button createRouterLink(Class<? extends Component> targetViewClass, String text,
      VaadinIcon icon) {
    return new Button(text, new Icon(icon), e -> {
      if (SecurityUtils.hasAccess(targetViewClass)) {
        getUI().ifPresent(ui -> ui.navigate(targetViewClass));
      } else {
        getUI().ifPresent(ui -> ui.navigate(AccessDeniedView.class));
      }
    });
  }

  /**
   * Shows the content of the layout which is the router target component annotated with a
   * {@link @Route}.
   * 
   * Note implementors should not care about old Route content, because Router automatically removes
   * it before calling the method.
   * 
   * Specified by: showRouterLayoutContent(...) in RouterLayout
   * 
   * @param: content the content component or null if the layout content is to be cleared.
   */
  @Override
  public void showRouterLayoutContent(HasElement content) {
    if (content != null) {
      this.contentContainer.removeAll();
      this.contentContainer.add((Component) content);
    }
  }
}
