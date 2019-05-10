package com.considlia.survey.security;

import com.considlia.survey.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


  @Autowired
  private final UserDetailsService userDetailsService;

  @Autowired
  public SecurityConfiguration(
      UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userDetailsService)
        .passwordEncoder(getPasswordEncoder());
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .requestCache().requestCache(new CustomRequestCache())
        .and().authorizeRequests()
        .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
        .antMatchers("/createsurvey", "/createsurvey/*").hasAuthority("USER")
//        .anyRequest().hasAnyAuthority(Role.getAllRoles())
        .and().formLogin().loginPage("/login").permitAll()
        .failureUrl("/failedlogin")
        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
        .and().logout().logoutSuccessUrl("/");


  }

  /*
  Might be the wrong signature for method, also wrong package, not sure.
   */
  private PasswordEncoder getPasswordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(CharSequence charSequence) {
        return charSequence.toString();
      }

      @Override
      public boolean matches(CharSequence charSequence, String s) {
        return true;
      }
    };
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(
        // Vaadin Flow static resources // (1)
        "/VAADIN/**",

        // the standard favicon URI
        "/favicon.ico",

        // the robots exclusion standard
        "/robots.txt",

        // web application manifest // (2)
        "/manifest.webmanifest",
        "/sw.js",
        "/offline-page.html",

        // (development mode) static resources // (3)
        "/frontend/**",

        // (development mode) webjars // (4)
        "/webjars/**",

        // (production mode) static resources // (5)
        "/frontend-es5/**", "/frontend-es6/**");
  }

}
