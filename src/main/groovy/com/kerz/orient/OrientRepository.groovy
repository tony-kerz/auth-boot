package com.kerz.orient

import com.tinkerpop.blueprints.Graph
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Repository

import javax.annotation.Resource

@Repository
class OrientRepository {
  static Log log = LogFactory.getLog(OrientRepository)

  def static nameExcludes = ['metaClass', 'class', 'id']
  def static classIncludes = [String, Date]

  @Resource(name='graph')
  Graph g

  @Resource(name='graphNoTx')
  Graph gNoTx

  def saveElement(entity, source=null, sink=null) {
    String className = entity.class.simpleName
    def gElt
    if (source && sink) {
      gElt = g.addEdgeVertex("class:$className", source, sink, className)
      log.debug("processing fields for edge=$className")
    } else {
      gElt = g.addVertex("class:$className")
      log.debug("processing fields for vertex=$className")
    }

    def eType = entity.class.metaClass
    entity.properties.each { prop, val ->
      MetaProperty metaProp = eType.getMetaProperty(prop)
      log.debug("prop=$prop, type=$metaProp.type")
      if (shouldSave(metaProp)) {
        log.debug('saving...')
        gElt.setProperty(prop, val)
      }
    }
    entity.id = gElt.id
    entity
  }

  void createVertexType(name) {
    if (!g.getVertexType(name)) {
      log.debug("create-vertex-type: name=$name")
      gNoTx.createVertexType(name)
    }
  }

  void createEdgeType(name) {
    if (!g.getEdgeType(name)) {
      log.debug("create-edge-type: name=$name")
      gNoTx.createEdgeType(name)
    }
  }

  void dropVertexType(name) {
    if (g.getVertexType(name)) {
      log.debug("drop-vertex-type: name=$name")
      gNoTx.dropVertexType(name)
    }
  }

  void dropEdgeType(name) {
    if (g.getEdgeType(name)) {
      log.debug("drop-edge-type: name=$name")
      gNoTx.dropEdgeType(name)
    }
  }

  private static boolean shouldSave(MetaProperty metaProp) {
    !(metaProp.name in nameExcludes) && (metaProp.type.isPrimitive() || metaProp.type in classIncludes)
  }
}
