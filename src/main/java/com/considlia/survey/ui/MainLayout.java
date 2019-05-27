package com.considlia.survey.ui;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.userviews.AccessDeniedView;
import com.considlia.survey.ui.userviews.LoginView;
import com.considlia.survey.ui.userviews.MyProfileView;
import com.considlia.survey.ui.userviews.RegistrationView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
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
  private HttpServletRequest httpServletRequest;

  public MainLayout() {
    setId("mainlayout");

    navigation = new HorizontalLayout();

    navigation.add(createRouterLink(HomeView.class, "Home", VaadinIcon.HOME));
    navigation
        .add(createRouterLink(CreateSurveyView.class, "Create New Survey", VaadinIcon.PLUS_CIRCLE));
    navigation.add(createRouterLink(MyProfileView.class, "profileview", VaadinIcon.USER));

    if (SecurityUtils.isUserLoggedIn()) {
      navigation.add(new Button("Logout", new Icon(VaadinIcon.EXIT), e -> logoutUser()));
    } else {
      navigation.add(createRouterLink(LoginView.class, "Login", VaadinIcon.SIGN_IN));
      navigation.add(createRouterLink(RegistrationView.class, "registration", VaadinIcon.PENCIL));
    }
    navigation.setClassName("header");
    contentContainer = new VerticalLayout();
    contentContainer.setClassName("content");

    add(navigation, contentContainer);
  }

  private void logoutUser() {
    SecurityContextHolder.clearContext();
    httpServletRequest.getSession(false).invalidate();
    UI.getCurrent().getSession().close();
    UI.getCurrent().getPage().reload();
  }

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

  @Override
  public void showRouterLayoutContent(HasElement content) {
    if (content != null) {
      this.contentContainer.removeAll();
      this.contentContainer.add((Component) content);
    }
  }
}
