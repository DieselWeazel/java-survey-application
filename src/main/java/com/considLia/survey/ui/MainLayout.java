package com.considLia.survey.ui;

import java.util.HashMap;
import java.util.Map;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends VerticalLayout implements RouterLayout {

  private Map<Class<? extends Component>, RouterLink> targets = new HashMap<>();
  private Map<String, Class<? extends Component>> targetPaths = new HashMap<>();

  private HorizontalLayout navigation;
  private VerticalLayout contentContainer;

  public MainLayout() {


    navigation = new HorizontalLayout();
    navigation.add(createRouterLink(MainView.class, "Home", VaadinIcon.HOME));
    navigation
        .add(createRouterLink(CreateSurveyView.class, "Create New Survey", VaadinIcon.PLUS_CIRCLE));

    contentContainer = new VerticalLayout();

    add(navigation, contentContainer);


  }

  private RouterLink createRouterLink(Class<? extends Component> targetViewClass, String text,
      VaadinIcon icon) {
    RouterLink routerLink = new RouterLink(null, targetViewClass);
    targets.put(targetViewClass, routerLink);
    targetPaths.put(routerLink.getHref(), targetViewClass);
    routerLink.add(new Button(text, new Icon(icon)));
    return routerLink;
  }

  @Override
  public void showRouterLayoutContent(HasElement content) {
    if (content != null) {
      this.contentContainer.removeAll();
      this.contentContainer.add((Component) content);
    }
  }


}
