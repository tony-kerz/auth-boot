package com.kerz.auth

import com.kerz.auth.dao.UserRepositoryInitializer
import com.kerz.auth.jwt.CustomTokenConverter
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.AccessTokenConverter

@CompileStatic
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Autowired
  AccessTokenConverter tokenConverter

  @Autowired
  UserRepositoryInitializer userRepositoryInitializer

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
  }
  
  @Bean
  AccessTokenConverter tokenConverter() {
    def converter = new CustomTokenConverter()
    converter.setSigningKey('my-s3cret-signing-key')
    converter
  }

  @Bean
  def initialize() {
    userRepositoryInitializer.tearDown()
    userRepositoryInitializer.setUp()
  }
}
