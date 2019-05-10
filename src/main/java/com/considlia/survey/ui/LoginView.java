package com.considlia.survey.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;

@Route(value = "login", layout = MainLayout.class)
public class LoginView extends VerticalLayout {

  // -- Login Components --
  private TextField userNameTextField;
  private PasswordField passwordField;
  //submitButton(?)
  private Button loginButton;

  // -- Backend Components --
  private Element formElement;
  private Element ironForm;

  private VerticalLayout loginView;

  public LoginView(){
    add(new H1("Test Login, not finished"));
    userNameTextField = new TextField();
    passwordField = new PasswordField();
    loginButton = new Button("Login");

    userNameTextField.getElement().setAttribute("name", "username");
    passwordField.getElement().setAttribute("name", "password");

    UI.getCurrent().getPage().executeJavaScript("document.getElementById('loginbutton').addEventListener('click', () => document.getElementById('ironform').submit());");
    // Am I moveable?
    loginView = new VerticalLayout(userNameTextField, passwordField, loginButton);

    userNameTextField.setLabel("Username");
    passwordField.setLabel("Password");
    loginButton.setId("loginbutton");

    userNameTextField.setWidth("380px");
    passwordField.setWidth("380px");

    formElement = new Element("form");
    formElement.setAttribute("method", "post");
    formElement.setAttribute("action", "login");
    formElement.appendChild(loginView.getElement());

    ironForm = new Element("iron-form");
    ironForm.setAttribute("id", "ironform");
    ironForm.setAttribute("allow-redirect", true);
    ironForm.appendChild(formElement);

    getElement().appendChild(ironForm);


    loginView.setWidth("400px");
    loginView.setAlignItems(Alignment.CENTER);
  }
}
