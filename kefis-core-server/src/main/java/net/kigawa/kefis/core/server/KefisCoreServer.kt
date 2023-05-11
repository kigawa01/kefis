package net.kigawa.kefis.core.server

import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KefisCoreServer {
  
  
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      run(KefisCoreServer::class.java, args)
    }
    
    fun run(rootClass: Class<*>, args: Array<String>, container: UnitContainer = UnitContainer.create()) {
      Config.container = container
      Config.rootClass = rootClass
      SpringApplication.run(KefisCoreServer::class.java, *args)
    }
  }
}