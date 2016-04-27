package com.kerz.orient

import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.tinkerpop.gremlin.groovy.Gremlin
import groovy.transform.CompileStatic
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@CompileStatic
@Configuration
class OrientRepositoryConfiguration {
  static {
    Gremlin.load()
  }

  static Log log = LogFactory.getLog(OrientRepositoryConfiguration)

  @Autowired
  OrientProperties props

  @Autowired
  OrientGraphFactory orientGraphFactory

  @Bean
  Graph graph() {
    orientGraphFactory.tx
  }

  @Bean
  Graph graphNoTx() {
    orientGraphFactory.noTx
  }

  @Bean
  OrientGraphFactory orientGraphFactory() {
    log.debug("props=${props}")
    new OrientGraphFactory("remote:$props.host/$props.db", props.user, props.password)
  }
}


