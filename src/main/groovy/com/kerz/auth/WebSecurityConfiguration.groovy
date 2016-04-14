package com.kerz.auth

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@CompileStatic
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

//  @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    auth
//      .inMemoryAuthentication()
//      .withUser('user-1')
//      .password('s3cret')
//      .roles('USER')
//  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers('/webjars/**', '/images/**', '/oauth/uncache_approvals', '/oauth/cache_approvals')
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean()
  }

  @CompileStatic(TypeCheckingMode.SKIP)
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers('/login.jsp').permitAll()
      .anyRequest().hasRole('USER')
      .and()
      .exceptionHandling()
      .accessDeniedPage('/login.jsp?authorization_error=true')
      .and()
      .csrf()
      .requireCsrfProtectionMatcher(new AntPathRequestMatcher('/oauth/authorize'))
      .disable()
      .logout()
      .logoutUrl('/logout')
      .logoutSuccessUrl('/login.jsp')
      .and()
      .formLogin()
      .loginProcessingUrl('/login')
      .failureUrl('/login.jsp?authentication_error=true')
      .loginPage('/login.jsp')
  }
}
