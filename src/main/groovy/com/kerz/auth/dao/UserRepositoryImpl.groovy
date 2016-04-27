package com.kerz.auth.dao

import com.kerz.auth.domain.HasPrivilege
import com.kerz.auth.domain.Privilege
import com.kerz.auth.domain.User
import com.kerz.orient.OrientRepository
import com.tinkerpop.blueprints.Graph
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl extends OrientRepository implements UserRepository {

  static Log log = LogFactory.getLog(UserRepository)

  @Autowired
  Graph g

  @Override
  User findByUsername(String username) {
    def result = null
    def user = g.V('@class', User.simpleName).has('name', username)
    if (user) {
      def privs = []
      user.outE('HasPriv').inV.name.each {
        privs.push(new SimpleGrantedAuthority(it))
      }
      result = new User(username, 's3cret', privs)
    }
    result
  }

  @Override
  User save(User user) {
    user.password = passwordEncoder.encode(user.password)
    vUser = saveElement(user)

    privNames.each { privName ->
      def priv = g.V('@class', Privilege.simpleName).has('name', privName)
      if (!priv) {
        priv = saveElement(new Privilege(name: privName))
      }
      saveElement(new HasPrivilege(name: privName), user, priv, HasPrivilege.simpleName)
    }
    user
  }
}
