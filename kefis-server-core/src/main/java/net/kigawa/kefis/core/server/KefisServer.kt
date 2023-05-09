package net.kigawa.kefis.core.server

import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KefisServer {
  
  
  companion object {
    fun run(rootClass: Class<*>, args: Array<String>, container: UnitContainer = UnitContainer.create()) {
      Config.container = container
      Config.rootClass = rootClass
      SpringApplication.run(KefisServer::class.java, *args)
    }
  }
}