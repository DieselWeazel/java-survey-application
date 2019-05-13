package com.considlia.survey.ui.UserViews;

import com.considlia.survey.model.User;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route (value = "registration", layout = MainLayout.class)
public class RegistrationView extends BaseView {

  private EmailField email;
  private PasswordField password;
  private TextField firstName;
  private TextField lastName;
  private TextField username;
  private Button submitButton;

  private VerticalLayout registrationLayout;

  private Binder<User> userBinder;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private User user;

  public RegistrationView(){
    super("Registration");
    initUI("580px");
  }

//  private void emptyFieldChecker(){
//    for (int i = 0; i < registrationLayout.getComponentCount(); i++){
//
//    }
//  }

  private void initUI(String width) {
    this.registrationLayout = new VerticalLayout();
    this.email = new EmailField("Email");
    this.password = new PasswordField("Password");
    this.firstName = new TextField("First Name");
    this.lastName = new TextField("Last Name");
    this.username = new TextField("Username");
    this.submitButton = new Button("Submit");
    this.userBinder = new Binder<>();
    
    bindFields();
    registrationLayout.add(email, firstName, lastName, username, password, submitButton);
    for (int i = 0; i < registrationLayout.getComponentCount(); i++){
      Component c = registrationLayout.getComponentAt(i);
      if (c instanceof HasSize){
        HasSize componentSize = (HasSize) c;
        componentSize.setWidth(width);
      }
    }
    setAlignItems(Alignment.CENTER);
    registrationLayout.setAlignItems(Alignment.CENTER);
    add(registrationLayout);
  }

  private void bindFields() {
    userBinder.setBean(user);
    userBinder.forField(email).withValidator(new EmailValidator("Must be an Email address"))
        .bind(User::getEmail, User::setEmail);
    userBinder.bindInstanceFields(this);
  }
}
