package com.kerz.auth.dao

import com.kerz.auth.domain.User

// using this as pattern for methods:
// http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
interface UserRepository {
  User save(User user)
  User findByUsername(String username)
}
