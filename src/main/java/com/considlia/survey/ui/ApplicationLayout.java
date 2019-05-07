package com.considlia.survey.ui;

import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
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

@Theme(Lumo.class)
@PageTitle("Vaadin10 Starter Project")
@BodySize(height = "100vh", width = "100vw")
@StyleSheet("applicationlayout.css")
public class ApplicationLayout extends FlexLayout implements RouterLayout {
  private static final long serialVersionUID = 1L;

  private Div header;
  private Div contentContainer;
  private Div footer;

  // @Inject
  // private I18NProvider i18nProvider;
  @Inject
  // private Event<LocaleChangeEvent> localeChanger;

  @PostConstruct
  private void init() {
    super.setId("applicationlayout");
    // navigation
    final Div navigation = createNavigation();
    navigation.addClassName("navigation");
    // locale switch
    final Div localeButtons = new Div();
    localeButtons.addClassName("localeswitch");
    // for (final Locale locale : i18nProvider.getProvidedLocales()) {
    // final Button localeButton = new Button(locale.getLanguage());
    // localeButton.addClickListener(click -> {
    // if (getUI().isPresent()) {
    // getUI().get().setLocale(locale);
    // localeChanger.fire(new LocaleChangeEvent(getUI().get(), locale));
    // }
    // });
    // localeButtons.add(localeButton);
    // }
    // header with navigation and locale switch
    header = new Div(navigation, localeButtons);
    header.addClassName("header");
    // the content
    contentContainer = new Div();
    contentContainer.addClassName("content");
    // sticky footer
    footer = new Div();
    footer.addClassName("footer");
    footer.add(new Text("Vaadin 10 Starterproject 2018"));
    add(header);
    add(contentContainer);
    add(footer);
  }

  private Div createNavigation() {
    // final RouterLink personLink = new RouterLink(null, PersonView.class);
    // personLink.add(new Button("Person", new Icon(VaadinIcon.ANCHOR)));
    final RouterLink addressLink = new RouterLink(null, AddressView.class);
    addressLink.add(new Button("Address", new Icon(VaadinIcon.ADD_DOCK)));
    final RouterLink paymentLink = new RouterLink(null, PaymentView.class);
    paymentLink.add(new Button("Payment", new Icon(VaadinIcon.BOOKMARK)));
    final RouterLink summaryLink = new RouterLink(null, SummaryView.class);
    summaryLink.add(new Button("Summary", new Icon(VaadinIcon.CLOSE_BIG)));
    return new Div(addressLink, paymentLink, summaryLink);
  }

  @Override
  public void showRouterLayoutContent(final HasElement content) {
    if (content != null) {
      contentContainer.removeAll();
      contentContainer.add(Objects.requireNonNull((Component) content));
    }
  }
}
