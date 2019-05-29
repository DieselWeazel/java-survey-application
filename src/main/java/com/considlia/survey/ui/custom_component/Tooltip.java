package com.considlia.survey.ui.custom_component;

import com.vaadin.flow.component.Component;

public class Tooltip {

  /**
   * Adds a textLabel when hovering over a enabled component.
   * <p>
   * Dosen't getSurveyLayout description if the component is disabled.
   */

  public static void setTooltip(Component component, String description) {
    component.getElement().setProperty("title", description);
  }
}
