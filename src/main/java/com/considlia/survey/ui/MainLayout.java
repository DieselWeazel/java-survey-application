package com.considlia.survey.ui;

import java.util.Objects;
import javax.annotation.PostConstruct;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

// @StyleSheet("css/app.css")
@StyleSheet("applicationlayout.css")
@Theme(Lumo.class)
@PageTitle("Test")
@BodySize(height = "100vh", width = "100vw")
public class MainLayout extends FlexLayout implements RouterLayout {

  private static final long serialVersionUID = 1L;

  // private Map<Class<? extends Component>, RouterLink> targets = new HashMap<>();
  // private Map<String, Class<? extends Component>> targetPaths = new HashMap<>();

  // private HorizontalLayout navigation;
  private Div contentContainer;

  private Div header;
  // private Div contentContainer;
  private Div footer;

  @PostConstruct
  private void init() {
    super.setId("mainlayout");
    //
    // navigation = new HorizontalLayout();
    // navigation.add(createRouterLink(MainView.class, "Home", VaadinIcon.HOME));
    // navigation
    // .add(createRouterLink(CreateSurveyView.class, "Create New Survey", VaadinIcon.PLUS_CIRCLE));
    // navigation.setClassName("header");
    //
    // contentContainer = new VerticalLayout();
    // contentContainer.setClassName("content");
    //
    // add(navigation, contentContainer);

    // navigation
    final Div navigation = createNavigation();
    navigation.addClassName("navigation");
    header = new Div(navigation);
    header.addClassName("header");
    // the content
    contentContainer = new Div(); // Gör vi detta som en layout sätts den in.
    contentContainer.addClassName("content");
    // sticky footer
    footer = new Div();
    footer.addClassName("footer");
    footer.add(new Text("Test"));
    add(header);
    add(contentContainer);
    add(footer);
  }

  private Div createNavigation() {
    final RouterLink homeLink = new RouterLink(null, MainView.class);
    homeLink.add(new Button("Home", new Icon(VaadinIcon.HOME)));
    final RouterLink surveyLink = new RouterLink(null, CreateSurveyView.class);
    surveyLink.add(new Button("Create New Survey", new Icon(VaadinIcon.PLUS_CIRCLE)));
    return new Div(homeLink, surveyLink);
  }

  // private RouterLink createRouterLink(final Class<? extends Component> targetViewClass,
  // final String text, final VaadinIcon icon) {
  // final RouterLink routerLink = new RouterLink(null, targetViewClass);
  // targets.put(targetViewClass, routerLink);
  // targetPaths.put(routerLink.getHref(), targetViewClass);
  // routerLink.add(new Button(text, new Icon(icon)));
  // return routerLink;
  // }

  @Override
  public void showRouterLayoutContent(final HasElement content) {
    if (content != null) {
      contentContainer.removeAll();
      contentContainer.add(Objects.requireNonNull((Component) content));
    }
  }

}
