package com.kerz.orient

import groovy.transform.CompileStatic
import org.apache.commons.lang.builder.ReflectionToStringBuilder
import org.springframework.boot.context.properties.ConfigurationProperties

import javax.validation.constraints.NotNull

@CompileStatic
@ConfigurationProperties(prefix='orient')
class OrientProperties {
  @NotNull
  String host

  @NotNull
  String db

  @NotNull
  String user

  @NotNull
  String password

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this)
  }
}


