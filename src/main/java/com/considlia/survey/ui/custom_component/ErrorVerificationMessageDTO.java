package com.considlia.survey.ui.custom_component;

/**
 * ErrorVerification Message, meant to handle validations,
 * with a return String displaying the exact message.
 */
public class ErrorVerificationMessageDTO {

  private boolean isConflict;
  private String errorText;

  /**
   * Constructor for easy instantiation.
   * @param isConflict if input from User is wrong.
   * @param errorText display to User, what went wrong.
   */
  public ErrorVerificationMessageDTO(boolean isConflict, String errorText) {
    this.isConflict = isConflict;
    this.errorText = errorText;
  }

  /**
   * Gets status of Conflict.
   * @return Conflict.
   */
  public boolean isConflict() {
    return isConflict;
  }

  /**
   * Sets the status of Conflict.
   * @param conflict
   */
  public void setConflict(boolean conflict) {
    isConflict = conflict;
  }

  /**
   * Gets the designated Error message.
   * @return error message for User.
   */
  public String getErrorText() {
    return errorText;
  }

  /**
   * Sets the error text to display.
   * @param errorText
   */
  public void setErrorText(String errorText) {
    this.errorText = errorText;
  }
}
