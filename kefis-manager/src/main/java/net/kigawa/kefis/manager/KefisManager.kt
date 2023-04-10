package net.kigawa.kefis.manager

import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KefisManager {
  companion object {
    init {
      println(KefisManager::class.java)
    }
  }
  
  init {
    println(KefisManager::class.java.name + "init")
  }
}