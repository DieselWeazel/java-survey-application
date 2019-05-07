package com.considlia.survey.ui;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.considlia.survey.ui.ApplicationLayout;

@Route(value = "summary", layout = ApplicationLayout.class)
public class SummaryView extends FormLayout implements BeforeEnterObserver {
  private static final long serialVersionUID = 1L;

  private Label label;

  public SummaryView() {
    label = new Label();
    super.add(label);
  }

  @Override
  public void beforeEnter(final BeforeEnterEvent event) {
    label.setText("SummaryView");

  }
}
