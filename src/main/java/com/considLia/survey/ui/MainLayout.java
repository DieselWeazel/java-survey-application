package com.considLia.survey.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MainLayout extends VerticalLayout implements RouterLayout, AfterNavigationObserver {

  private Map<Class<? extends Component>, RouterLink> targets = new HashMap<>();
  private Map<String, Class<? extends Component>> targetPaths = new HashMap<>();
  private RouterLink selected;

  private HorizontalLayout navigation;
  private VerticalLayout contentContainer;

  public MainLayout(){
    setId("mainLayout");


    navigation = new HorizontalLayout();
    navigation.addClassName("navigation");
    navigation.add(createRouterLink(MainView.class, "Home"));
    navigation.add(createRouterLink(CreateSurveyView.class, "createsurvey"));
    navigation.add(createRouterLink(ShowSurveyView.class, "showsurvey"));

    contentContainer = new VerticalLayout();
    contentContainer.addClassName("content");

    add(navigation, contentContainer);


  }

  private RouterLink createRouterLink(final Class<? extends Component> targetViewClass, final String text) {
    RouterLink routerLink = new RouterLink(null, targetViewClass);
    targets.put(targetViewClass, routerLink);
    targetPaths.put(routerLink.getHref(), targetViewClass);
    routerLink.add(new Button(text));
    return routerLink;
  }

  @Override
  public void showRouterLayoutContent(HasElement content) {
    if (content != null) {
      this.contentContainer.removeAll();
      this.contentContainer.add((Component) content);
    }
  }

  @Override
  public void afterNavigation(AfterNavigationEvent event) {
    if (event.getLocation().getPath().isEmpty()) {
      activateMenuTarget(MainView.class);
    } else {
      StringBuilder path = new StringBuilder();
      for (String segment : event.getLocation().getSegments()) {
        path.append(segment);
        Optional<Class> target = getTargetForPath(path.toString());
        if (target.isPresent()) {
          clearSelection();
          activateMenuTarget(target.get());
          break;
        }
        path.append("/");
      }
    }
  }

  private boolean targetExists(final Class<?> navigationTarget) {
    return targets.containsKey(navigationTarget);
  }

  protected void activateMenuTarget(final Class<?> navigationTarget) {
    RouterLink activatedLink = targets.get(navigationTarget);
    activatedLink.addClassName("selected");
    selected = activatedLink;
  }

  protected void clearSelection() {
    if (selected != null) {
      selected.removeClassName("selected");
    }
  }

  protected Optional<Class> getTargetForPath(final String path) {
    if (targetPaths.containsKey(path)) {
      return Optional.of(targetPaths.get(path));
    } else if (targetPaths.containsKey(path + "/")) {
      return Optional.of(targetPaths.get(path + "/"));
    }
    return Optional.empty();
  }

}
