package com.considlia.survey.ui.UserViews;

import com.considlia.survey.model.User;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.security.SecurityUtils;
import com.considlia.survey.security.UserDetailsServiceImpl;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.considlia.survey.ui.custom_component.ConfirmDialog;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
There is no DAO Authentication within this as of now.
Jonathan
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

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  public RegistrationView() {
    super("Registration");
    initUI("580px");
  }

  //SQLIntegrityConstraintViolationException
  //DataIntegrityViolationException
  //ConstraintViolationException
  private void registerUser() {
    try {
      userBinder.writeBean(user);
    } catch (ValidationException e) {
      return;
    }

    passwordString = passwordField.getValue();
    user.setPassword(passwordEncoder.encode(passwordString));
    user.setRole("USER");
    try {
      userRepository.save(user);
      signIn();
      // The real duplicate is nested within this exception, if we want to show the duplicate correctly
      // we will need to dig into the exception. Leaving this here for the future.
    } catch (DataIntegrityViolationException e) {
      ConfirmDialog confirmDialog = new ConfirmDialog();
      confirmDialog.open();
//      e.printStackTrace();
//      if(e.getMessage().)
//      System.out.println(e.getCause());
//      System.out.println(e.getSuppressed());

    }
  }

  /*
  Sign in function doesn't work properly now.
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

  private void initUI(String width) {
    this.registrationLayout = new VerticalLayout();
    this.email = new EmailField("Email");
    this.passwordField = new PasswordField("Password");
    this.firstName = new TextField("First Name");
    this.lastName = new TextField("Last Name");
    this.username = new TextField("Username");
    this.submitButton = new Button("Submit");
    this.userBinder = new Binder<>(User.class);
    this.user = new User();

    submitButton.addClickListener(
        e -> registerUser());

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

  private void bindFields() {
    userBinder.setBean(user);
    userBinder
        .forField(email)
        .withValidator(new EmailValidator("Must be an Email address"))
        .bind(User::getEmail, User::setEmail);
    userBinder
        .forField(firstName)
        .withValidator(new StringLengthValidator("Must be more than 3 characters & max 255",
            3, 255))
        .bind(User::getFirstName, User::setFirstName);
    userBinder
        .forField(lastName)
        .withValidator(new StringLengthValidator("Must be more than 3 characters & max 255",
            3, 255))
        .bind(User::getLastName, User::setLastName);
    userBinder
        .forField(username)
        .withValidator(new StringLengthValidator("Must be more than 3 characters & max 255",
            3, 255))
        .bind(User::getUsername, User::setUsername);
    userBinder
        .forField(passwordField)
        .withValidator(new StringLengthValidator("Must be more than 3 characters & max 255",
            3, 255))
        .bind(User::getPassword, User::setPassword);
//    userBinder.bindInstanceFields(this);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (SecurityUtils.isUserLoggedIn()) {
      event.rerouteTo("");
    }
  }
}
