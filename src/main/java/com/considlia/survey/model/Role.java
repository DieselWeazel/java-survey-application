package com.considlia.survey.model;

public class Role {

  public static final String USER = "USER";
  public static final String ADMIN = "ADMIN";

  private Role() {
    // Static methods and fields only
  }

  public static String[] getAllRoles() {
    return new String[]{USER, ADMIN};
  }
}
