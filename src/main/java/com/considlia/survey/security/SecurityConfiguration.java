package com.considlia.survey.security;

import com.considlia.survey.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired private final UserDetailsServiceImpl userDetailsService;

  /**
   * Constructor for SecurityConfiguration
   *
   * @param userDetailsService stores {@link UserDetailsServiceImpl} implementation of {@link
   *     org.springframework.security.core.userdetails.UserDetailsService} to retrieve User with
   *     correct UserName, and password.
   */
  public SecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /**
   * Configures with correct UserDetailsService and which PasswordEncoder to use. Without, Spring
   * Security won't be able to authenticate our users.
   *
   * @param auth Building our AuthenticationManager
   * @throws Exception if our implementation of {@link
   *     org.springframework.security.core.userdetails.UserDetailsService} or our chosen
   *     PasswordEncoder isn't valid.
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  /**
   * Bean of AuthenticationManager in order to authenticate Users within Login.
   *
   * @return implementation of {@link AuthenticationManager}
   * @throws Exception if authentication not possible.
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * Configures the paths of access, for various roles.
   *
   * @param http representing the address of application.
   * @throws Exception should only be thrown if Configuration isn't correct.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .requestCache()
        .requestCache(new CustomRequestCache())
        .and()
        .authorizeRequests()
        .requestMatchers(SecurityUtils::isFrameworkInternalRequest)
        .permitAll()

        .antMatchers("/profileview")
        .hasAuthority("USER")
        .antMatchers("/createsurvey", "/createsurvey/*", "/createsurvey/**")
        .hasAuthority("USER")

        //        .anyRequest().hasAnyAuthority(Role.getAllRoles())
        .and()
        .formLogin()
        .loginPage("/login")
        .failureUrl("/failedlogin");
//        .and()
//        .logout()
//        .logoutSuccessUrl("/logout");
  }

  /**
   * Spring Security won't work without a Password Encoder that is valid. Any PasswordEncoder can be
   * chosen as long as it encodes the String correctly. Does not work if Database storage of String
   * is shorter than 60 chars.
   *
   * @return BCryptPasswordEncoder.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures readability/access for Vaadin framework.
   *
   * @param web declaring what Spring Security should ignore.
   * @throws Exception if configuration isn't correct.
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers(
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
            "/frontend-es5/**",
            "/frontend-es6/**");
  }
}
