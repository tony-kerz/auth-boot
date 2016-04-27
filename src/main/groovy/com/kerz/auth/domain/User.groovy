package com.kerz.auth.domain

import org.springframework.security.core.GrantedAuthority

class User extends org.springframework.security.core.userdetails.User {
  final static String CLASS_NAME = org.springframework.security.core.userdetails.User.simpleName
  final static String P_NAME = 'name'
  final static String P_PASSWORD = 'password'
  final static String E_HAS_PRIV = 'HasPriv'

  User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities)
  }
}
