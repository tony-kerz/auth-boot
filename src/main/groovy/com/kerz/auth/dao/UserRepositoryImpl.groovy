package com.kerz.auth.dao

import com.kerz.auth.domain.CustomUser
import com.tinkerpop.blueprints.Graph
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl implements UserRepository {

  static Log log = LogFactory.getLog(UserRepository)

  Graph g

  @Autowired
  UserRepositoryImpl(Graph graph) {
    this.g = graph
  }

  @Override
  CustomUser findByUsername(String username) {
    def result = null
    def user = g.V('@class', CustomUser.CLASS_NAME).has(CustomUser.P_NAME, username)
    if (user) {
      def privs = []
      user.outE('HasPriv').inV.name.each {
        privs.push(new SimpleGrantedAuthority(it))
      }
      result = new CustomUser(username, 's3cret', privs)
    }
    result
  }
}
