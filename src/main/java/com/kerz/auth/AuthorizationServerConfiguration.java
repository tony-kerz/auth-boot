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
//  private final Log log = LogFactory.getLog(getClass());
//  
//  @Autowired
//  private TokenStore tokenStore;

  @Autowired
  private AuthenticationManager authenticationManager;

//  @Autowired
//  private UserApprovalHandler userApprovalHandler;
//  
//  @Autowired
//  private ApprovalStore approvalStore;
//  
//  @Autowired
//  private ClientDetailsService clientDetailsService;
  
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
        .inMemory()
        .withClient("web-client-1")
        .authorizedGrantTypes("implicit")
        .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
        .scopes("read", "write", "trust", "basic")
        .accessTokenValiditySeconds(60);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
      //.tokenStore(tokenStore)
      .accessTokenConverter(tokenConverter())
      //.userApprovalHandler(userApprovalHandler)
      .authenticationManager(authenticationManager);
  }
  
  @Bean
  public JwtAccessTokenConverter tokenConverter() {
    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey("xxxx");
    return converter;
  }
  
//  @Bean
//  public JwtTokenStore tokenStore() {
//    final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//    jwtAccessTokenConverter.setSigningKey("xxxx");
//    JwtTokenStore store = new JwtTokenStore(jwtAccessTokenConverter);
//    store.setApprovalStore(approvalStore);
//    return store;
//  }
//  
//  @Bean 
//  @Lazy
//  @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
//  public UserApprovalHandler userApprovalHandler() throws Exception {
//    PreApprovingUserApprovalHandler handler = new PreApprovingUserApprovalHandler();
//    //handler.setApprovalStore(approvalStore());
//    handler.setApprovalStore(approvalStore);
//    handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//    handler.setClientDetailsService(clientDetailsService);
//    handler.setUseApprovalStore(true);
//    return handler;
//  }
//  
//  @Bean
//  public ApprovalStore approvalStore() throws Exception {
//    TokenApprovalStore store = new TokenApprovalStore();
//    store.setTokenStore(tokenStore);
//    return store;
//  }
}
