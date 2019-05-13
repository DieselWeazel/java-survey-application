package com.considlia.survey.ui;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import com.considlia.survey.model.User;
import com.considlia.survey.repositories.UserRepository;
import com.considlia.survey.security.SecurityConfiguration;
import com.considlia.survey.security.UserDetailsServiceImpl;
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

@Route(value = "login", layout = MainLayout.class)
public class LoginView extends VerticalLayout {

  // -- Login Components --
  private TextField username;
  private PasswordField password;
  private Button submitButton;

  // -- Backend Components --
  @Autowired
  private AuthenticationManager authenticationManagerBean;

  private VerticalLayout loginView;

  public LoginView(AuthenticationManager authenticationManagerBean){
    this.authenticationManagerBean = authenticationManagerBean;
    add(new H1("Test Login, not finished"));
    username = new TextField();
    password = new PasswordField();
//    loginButton = new NativeButton("Login (native)");
    submitButton = new Button("Login");

    username.setLabel("Username");
    password.setLabel("Password");

    submitButton.addClickListener(event -> {
      if (setCurrentUser(username.getValue(),password.getValue())){
//        UI.getCurrent().getPage().getHistory().replaceState(null, "");
        UI.getCurrent().getPage().reload();
//        UI.getCurrent().navigate(MainView.class);
      }
    });

    username.setWidth("380px");
    password.setWidth("380px");
    loginView = new VerticalLayout(username, password, submitButton);
    add(loginView);

    loginView.setWidth("400px");
    loginView.setAlignItems(Alignment.CENTER);

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


