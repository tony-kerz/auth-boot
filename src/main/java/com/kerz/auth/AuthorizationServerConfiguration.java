package com.kerz.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtAccessTokenConverter tokenConverter;
  
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    String[] scopes = {"read", "write"};
    
    clients
        .inMemory()
        .withClient("web-client-1")
        .authorizedGrantTypes("implicit")
        .scopes(scopes)
        .autoApprove(true)
        .autoApprove(scopes)
        .accessTokenValiditySeconds(60);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
      .accessTokenConverter(tokenConverter)
      .authenticationManager(authenticationManager);
  }
  
  @Bean
  public JwtAccessTokenConverter tokenConverter() {
    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey("my-s3cret-signing-key");
    return converter;
  }
}
