package com.kerz.auth.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser extends User {
  final static String CLASS_NAME = User.simpleName
  final static String P_NAME = 'name'
  final static String E_HAS_PRIV = 'HasPriv'

  CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities)
  }
}
