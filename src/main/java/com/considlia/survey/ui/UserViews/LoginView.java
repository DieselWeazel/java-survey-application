package com.considlia.survey.ui.UserViews;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import com.considlia.survey.model.User;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.security.SecurityConfiguration;
import com.considlia.survey.security.UserDetailsServiceImpl;
import com.considlia.survey.ui.BaseView;
import com.considlia.survey.ui.HomeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/*
Trying to figure out a way to redirect without "UI.getCurrent().navigate(HomeView.class);", looking for a Spring Security integration of sorts.
Jonathan
 */
@Route(value = "login")
public class LoginView extends BaseView {

  // -- Login Components --
  private TextField username;
  private PasswordField password;
  private Button submitButton;

  // -- Backend Components --
  @Autowired
  private AuthenticationManager authenticationManagerBean;

  private VerticalLayout loginView;

  public LoginView(AuthenticationManager authenticationManagerBean){
    super("Login");
    this.authenticationManagerBean = authenticationManagerBean;
    username = new TextField();
    password = new PasswordField();
    submitButton = new Button("Login");

    username.setLabel("Username");
    password.setLabel("Password");

    submitButton.addClickListener(event -> {
      if (setCurrentUser(username.getValue(),password.getValue())){
        UI.getCurrent().navigate(HomeView.class);
      }
    });

    username.setWidth("380px");
    password.setWidth("380px");
    loginView = new VerticalLayout(username, password, submitButton);
    add(loginView);

    loginView.setWidth("400px");
    loginView.setAlignItems(Alignment.CENTER);
    setAlignItems(Alignment.CENTER);
  }

  public boolean setCurrentUser(String login,String password){
    try {
      Authentication request=new UsernamePasswordAuthenticationToken(login,password);
      Authentication result= authenticationManagerBean.authenticate(request);
      SecurityContextHolder.getContext().setAuthentication(result);
    }
    catch (  BadCredentialsException e) {
      return false;
    }
    return true;
  }
}


