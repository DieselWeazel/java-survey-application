package com.considlia.survey.ui;

import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.UserViews.AccessDeniedView;
import com.considlia.survey.ui.UserViews.LoginView;
import com.considlia.survey.ui.UserViews.MyProfileView;
import com.considlia.survey.ui.UserViews.RegistrationView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import java.util.HashMap;
import java.util.Map;

@StyleSheet("css/app.css")
public class MainLayout extends VerticalLayout implements RouterLayout {

  private Map<Class<? extends Component>, RouterLink> targets = new HashMap<>();
  private Map<String, Class<? extends Component>> targetPaths = new HashMap<>();

  private HorizontalLayout navigation;
  private VerticalLayout contentContainer;

  public MainLayout() {
    setId("mainlayout");

    navigation = new HorizontalLayout();

    navigation.add(createRouterLink(HomeView.class, "Home", VaadinIcon.HOME));
    navigation.add(createRouterLink(CreateSurveyView.class, "Create New Survey", VaadinIcon.PLUS_CIRCLE));
    navigation.add(createRouterLink(MyProfileView.class, "profileview", VaadinIcon.USER));
//    navigation.add(createRouterLink(LoginView.class, "Login", VaadinIcon.SIGN_IN));
//    navigation.add(createRouterLink(RegistrationView.class, "registration", VaadinIcon.PENCIL));

    navigation.setClassName("header");
    contentContainer = new VerticalLayout();
    contentContainer.setClassName("content");

    add(navigation, contentContainer);
  }

  public static void refreshLayoutContent(){

  }

//  private Button createRouterLinks(String destination, String text, VaadinIcon icon){
//    return new Button(text, new Icon(icon), e -> getUI().ifPresent(ui -> ui.na))
//  }

  private Button createRouterLink(
      Class<? extends Component> targetViewClass, String text, VaadinIcon icon) {
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
