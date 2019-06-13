package com.considlia.survey.ui.userviews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.considlia.survey.model.Role;
import com.considlia.survey.model.User;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.considlia.survey.ui.custom_component.ConfirmDialog;
import com.considlia.survey.ui.custom_component.ConfirmDialog.ConfirmDialogBuilder;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

/*
 * There is no DAO Authentication within this as of now. Jonathan
 */
@Route(value = "registration", layout = MainLayout.class)
public class RegistrationView extends BaseView implements BeforeEnterObserver {

  private EmailField email;
  private PasswordField passwordField;
  private TextField firstName;
  private TextField lastName;
  private TextField username;
  private Button submitButton;

  private VerticalLayout registrationLayout;

  private Binder<User> userBinder;

  @Autowired
  private PasswordEncoder passwordEncoder;
  private String passwordString = null;

  private User user;
  @Autowired
  private AuthenticationManager authenticationManagerBean;

  @Autowired
  private UserRepository userRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationView.class);

  /**
   * Constructor for View.
   */
  public RegistrationView() {
    super("Registration");
    initUI("580px");
  }

  /**
   * Registers User, if all fields are valid.
   */
  private void registerUser() {
    try {
      userBinder.writeBean(user);
    } catch (ValidationException e) {
      return;
    }

    // First Checks if Username is taken, then checks if Email is taken, if both booleans are false,
    // User registers.
    if (userRepository.existsByUsername(username.getValue())) {
      ConfirmDialog<RegistrationView> confirmDialog =
          new ConfirmDialogBuilder<RegistrationView>().with($ -> {
            $.addHeaderText("Username taken!");
            $.addContentText("Error, username: " + username.getValue()
                + " is already taken, please take another one.");
            $.addSimpleCloseButton("Ok");
          }).createConfirmDialog();
      confirmDialog.open();
      return;
    } else if (userRepository.existsByEmail(email.getValue())) {
      ConfirmDialog<RegistrationView> confirmDialog =
          new ConfirmDialogBuilder<RegistrationView>().with($ -> {
            $.addHeaderText("Email already exists!");
            $.addContentText(
                "There already exists a User registered with this email, have you forgotten your password?");
            $.addSimpleCloseButton("Ok");
          }).createConfirmDialog();
      confirmDialog.open();
      return;
    } else {
      passwordString = passwordField.getValue();
      user.setPassword(passwordEncoder.encode(passwordString));
      user.setRole(Role.USER);
      userRepository.save(user);
      signIn();
    }
  }

  /**
   * Signs in User upon successful registration.
   */
  private void signIn() {
    System.out.println(user.getUsername() + " " + user.getPassword());
    Authentication request =
        new UsernamePasswordAuthenticationToken(user.getUsername(), passwordString);
    Authentication result = authenticationManagerBean.authenticate(request);
    SecurityContextHolder.getContext().setAuthentication(result);
    UI.getCurrent().getSession().close();
    UI.getCurrent().getPage().reload();
  }

  /**
   * Initiates UI
   * 
   * @param width being the width for all TextFields.
   */
  private void initUI(String width) {
    registrationLayout = new VerticalLayout();
    email = new EmailField("Email");
    email.focus();
    email.addKeyPressListener(Key.ENTER, event -> registerUser());

    passwordField = new PasswordField("Password");
    passwordField.addKeyPressListener(Key.ENTER, event -> registerUser());

    firstName = new TextField("First Name");
    firstName.addKeyPressListener(Key.ENTER, event -> registerUser());

    lastName = new TextField("Last Name");
    lastName.addKeyPressListener(Key.ENTER, event -> registerUser());

    username = new TextField("Username");
    username.addKeyPressListener(Key.ENTER, event -> registerUser());

    submitButton = new Button("Submit");
    userBinder = new Binder<>(User.class);
    user = new User();

    submitButton.addClickListener(e -> registerUser());

    registrationLayout.add(email, firstName, lastName, username, passwordField, submitButton);
    for (int i = 0; i < registrationLayout.getComponentCount(); i++) {
      Component c = registrationLayout.getComponentAt(i);
      if (c instanceof HasSize) {
        HasSize componentSize = (HasSize) c;
        componentSize.setWidth(width);
      }
    }
    setAlignItems(Alignment.CENTER);
    registrationLayout.setAlignItems(Alignment.CENTER);
    add(registrationLayout);

    bindFields();
  }

  /**
   * Binds every input field of the registration form.
   */
  private void bindFields() {
    userBinder.setBean(user);
    userBinder.forField(email).asRequired("Email can not be left empty")
        .withValidator(new EmailValidator("Must be an Email address"))
        .bind(User::getEmail, User::setEmail);

    userBinder.forField(firstName).asRequired("Firstname can not be left empty")
        .withValidator(new StringLengthValidator("Firstname can max be 30 characters", 1, 30))
        .withValidator(new RegexpValidator("Field can only contain letters", "^[a-zA-ZåÅäÄöÖ]+$"))
        .bind(User::getFirstName, User::setFirstName);

    userBinder.forField(lastName).asRequired("Lastname can not be left empty")
        .withValidator(new StringLengthValidator("Lastname can max be 30 characters", 1, 30))
        .withValidator(new RegexpValidator("Field can only contain letters", "^[a-zA-ZåÅäÄöÖ]+$"))
        .bind(User::getLastName, User::setLastName);
    userBinder.forField(username).asRequired("Username can not be left empty")
        .withValidator(
            new StringLengthValidator("Must be more than 2 characters & max 255", 2, 255))
        .bind(User::getUsername, User::setUsername);
    userBinder.forField(passwordField).asRequired("Password can not be left empty")
        .withValidator(
            new StringLengthValidator("Must be more than 6 characters & max 255", 6, 255))
        .bind(User::getPassword, User::setPassword);
  }

  /**
   * If User is signed in, returns User to previous URL.
   * 
   * @param event being the navigation target before this one.
   */
  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (SecurityUtils.isUserLoggedIn()) {
      event.rerouteTo("");
    }
  }
}
