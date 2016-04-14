package com.kerz.auth

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

@CompileStatic
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Autowired
  AuthenticationManager authenticationManager

  @Autowired
  JwtAccessTokenConverter tokenConverter

  @Autowired
  UserDetailsService userDetailsService

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    String[] scopes = ['read', 'write']
    
    clients
        .inMemory()
        .withClient('web-client-1')
        .authorizedGrantTypes('implicit')
        .scopes(scopes)
        .autoApprove(true)
        .autoApprove(scopes)
        .accessTokenValiditySeconds(60)
  }

  @Override
  void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
      .accessTokenConverter(tokenConverter)
      .authenticationManager(authenticationManager)
      .userDetailsService(userDetailsService)
  }
  
  @Bean
  JwtAccessTokenConverter tokenConverter() {
    def converter = new JwtAccessTokenConverter()
    converter.setSigningKey('my-s3cret-signing-key')
    converter
  }
}
