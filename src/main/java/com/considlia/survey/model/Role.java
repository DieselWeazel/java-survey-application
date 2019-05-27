package com.considlia.survey.model;

/**
 * Class to set the Roles of User The Role can be User, Admin
 */
public class Role {

  public static final String USER = "USER";
  public static final String ADMIN = "ADMIN";

  /**
   * Constructor for Role
   */
  private Role() {
    // Static methods and fields only
  }

  /**
   * To list all roles
   *
   * @return All roles that exists
   */
  public static String[] getAllRoles() {
    return new String[] {USER, ADMIN};
  }
}
