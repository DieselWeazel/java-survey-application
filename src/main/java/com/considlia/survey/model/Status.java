package com.considlia.survey.model;

/**
 * Class to set the status for the Survey The Survey can be Editable, Public, Private or Closed.
 * When a Survey is published its no longer Editable, but have to be either Public or Private
 */
public enum Status {
  EDITABLE,
  PUBLIC,
  PRIVATE,
  CLOSED;
}
