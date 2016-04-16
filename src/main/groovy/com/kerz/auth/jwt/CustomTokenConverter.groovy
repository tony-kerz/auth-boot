package com.kerz.auth.jwt

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

class CustomTokenConverter extends JwtAccessTokenConverter {
  static Log log = LogFactory.getLog(CustomTokenConverter)

  @Override
  OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    def map = [:]
    map.foo = 'bar'
    log.debug("adding map=$map to token=$accessToken")
    accessToken.additionalInformation = map
    return super.enhance(accessToken, authentication)
  }

  @Override
  protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    String token = super.encode(accessToken, authentication)
    log.debug("encode: token = $token")
    token
  }
}
