package com.considlia.survey.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @NotEmpty
  @Email(message = "Email address is already taken, have you forgot your login?")
  @Column(unique = true)
  private String email;
  //  @Column(name = "password")
  private String password;

  @NotBlank
  @Column(unique = true)
  //  @Column(name = "username")
  private String username;

  @NotBlank
  //  @Column(name = "first_name")
  private String firstName;

  @NotBlank
  //  @Column(name = "last_name")
  private String lastName;

  @NotBlank private String role;

  // If User doesn't delete its child entity (Surveys) when User is deleted, we might need a Service
  // class for this.
  // Have not tried removing Users within Java/Hibernate, but removing them in SQL leaves the
  // surveys. (Might be redundant of me writing this but
  // I faced so many issues with this bit prior to this solution, deleting a Survey would delete the
  // User, caused by fetchtype eager,
  // SO I live this note here since this issue might come up in the future.
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
  private Set<Survey> surveys = new HashSet<>();

  /*
  Could add a private boolean if user is banned/blocked.
   */

  /**
   * Empty Constructor
   */
  public User() {}

  /**
   * Get the User ID
   * @return User ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the User ID
   * @param id is set automatically with JPA.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get User Email
   * @return Email of User
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set User Email
   * @param email Email to set of User, must be unique.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets User Password (will be hashed by password encoder)
   * @return password encoded.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set Password
   * @param password sets password, see {@link com.considlia.survey.security.SecurityConfiguration}
   * to find PasswordEncoder.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets Username of user.
   * @return Username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Set Username of User
   * @param username of user, must be unique.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets First Name of User
   * @return first name of User.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets First Name of User.
   * @param firstName of User.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get Last name of User.
   * @return users Last Name.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets last name of User.
   * @param lastName of User.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets Role
   * @return role of user. Not to be confused with
   * {@link Role}
   */
  public String getRole() {
    return role;
  }

  /**
   * Sets Role of User.
   * @param role see {@link Role}, must be an available Role to work with
   * the configuration.
   */
  public void setRole(String role) {
    this.role = role;
  }

  /**
   * Gets Surveys created by User.
   * @return Surveys created by User.
   */
  public Set<Survey> getSurveys() {
    return surveys;
  }

  /**
   * Sets the list of Surveys owned by User.
   * @param surveys
   */
  public void setSurveys(Set<Survey> surveys) {
    this.surveys = surveys;
  }

  /**
   * Standard toString method.
   * @return String of User.
   */
  @Override
  public String toString() {
    return "User{" + "username='" + username + '\'' + '}';
  }
}
