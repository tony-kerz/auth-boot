package com.kerz.auth.web

import groovy.transform.CompileStatic
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@CompileStatic
@Controller
public class HomeController {

  @RequestMapping('/')
  public String home() {
    return 'home'
  }

}
