package com.considlia.survey.ui.custom_component;

import com.vaadin.flow.component.Component;

public class Tooltip {

  /**
   * 
   * Dosen't show description if the component is disabled.
   */

  public static void setTooltip(Component component, String description) {
    component.getElement().setProperty("title", description);
  }
}
