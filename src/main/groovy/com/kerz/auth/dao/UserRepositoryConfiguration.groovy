package com.kerz.auth.dao

import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.tinkerpop.gremlin.groovy.Gremlin

//import com.tinkerpop.gremlin.groovy.Gremlin
import groovy.transform.CompileStatic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//@CompileStatic
@Configuration
public class UserRepositoryConfiguration {
  static {
    Gremlin.load()
  }

  //def host = 'localhost'
  def host = '192.168.99.100'
  def user = 'root'
  def password = 's3cret'
  def db = "remote:$host/tk-test-1"

  @Bean
  Graph graph() {
    OrientGraphFactory factory = new OrientGraphFactory(db, user, password)
    factory.tx
  }

  @Bean
  Graph graphNoTx() {
    OrientGraphFactory factory = new OrientGraphFactory(db, user, password)
    factory.noTx
  }
}


