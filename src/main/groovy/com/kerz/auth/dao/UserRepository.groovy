package com.kerz.auth.dao

import com.kerz.auth.domain.CustomUser

interface UserRepository {
  CustomUser findByUsername(String username)
}
