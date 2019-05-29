package com.considlia.survey.ui.custom_component;

import java.util.List;

/**
 * ErrorVerification Message, meant to handle validations,
 * with a return String displaying the exact message.
 *
 * Jonathan Harr
 */
public class ErrorVerificationMessageDTO {

  private boolean isConflict;
  private List<String> errorText;

  /**
   * Constructor for easy instantiation.
   * @param isConflict if input from User is wrong.
   * @param errorText display to User, what went wrong.
   *
   * Info: Handles an array of Strings to show several inputs being wrong. Can be
   * overloaded into handling just one String as well.
   */
  public ErrorVerificationMessageDTO(boolean isConflict, List<String> errorText) {
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
  public List<String> getErrorText() {
    return errorText;
  }

  /**
   * Sets the error text to display.
   * @param errorText
   */
  public void setErrorText(List<String> errorText) {
    this.errorText = errorText;
  }
}
