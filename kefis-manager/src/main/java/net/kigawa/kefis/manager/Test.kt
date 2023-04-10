package net.kigawa.kefis

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Test {
  companion object{
    init {
      println("companion")
    }
  }
  init {
    println("init")
  }
  
  @RequestMapping(value = ["/"])
  fun index(): String {
    return "test"
  }
}