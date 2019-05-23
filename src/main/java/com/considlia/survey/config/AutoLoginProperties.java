package com.considlia.survey.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * Configuration properties used for optionally logging a user in automatically.
 */
@ConfigurationProperties(prefix = "consid.lia.autologin")
public class AutoLoginProperties {

  private boolean enabled;
  private String username;
  private String password;

  /**
   * Returns whether auto-login is enabled or not.
   *
   * @return {@code true} if auto-login is enabled, {@code false} if not
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Sets whether auto-login should be enabled or not.
   *
   * @param enabled whether to enable auto-login
   */
  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Returns the username of the user to log in automatically.
   *
   * @return the username of the user to log in automatically
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the user to log in automatically.
   *
   * @param username the username of the user to log in automatically
   */
  public void setUsername(final String username) {
    this.username = username;
  }

  /**
   * Returns the password of the user to log in automatically.
   *
   * @return the password of the user to log in automatically
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the user to log in automatically.
   *
   * @param password the password of the user to log in automatically
   */
  public void setPassword(final String password) {
    this.password = password;
  }
}
