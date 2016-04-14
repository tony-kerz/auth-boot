package com.kerz.auth.dao

import com.kerz.auth.domain.CustomUser
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.Vertex
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Repository
import org.springframework.util.Assert

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
    def privs = []
    g.V('@class', 'User').outE('HasPriv').inV.name.each {
      privs.push(new SimpleGrantedAuthority(it))
    }
    //def result = g.V.has('@class', CustomUser.CLASS_NAME).has(CustomUser.P_NAME, username).outE(CustomUser.E_HAS_PRIV).name
    //Assert.notNull(result, 'result required')

    log.debug("find-by-username: privs=$privs")

    return new CustomUser(username, 's3cret', privs)
  }
}
