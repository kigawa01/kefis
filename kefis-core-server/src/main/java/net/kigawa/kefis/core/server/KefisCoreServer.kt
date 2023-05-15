package net.kigawa.kefis.core.server

import net.kigawa.kefis.core.server.endpoint.EndpointRegister
import net.kigawa.kutil.unitapi.component.UnitContainer
import net.kigawa.kutil.unitapi.registrar.ResourceRegistrar
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KefisCoreServer(endpointRegister: EndpointRegister) {
  init {
    endpointRegister.registerEndpoint(Config.rootClass)
  }
  
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      run(KefisCoreServer::class.java, args)
    }
    
    fun run(rootClass: Class<*>, args: Array<String>) {
      val container = UnitContainer.create()
      
      container.getUnit(ResourceRegistrar::class.java).register(rootClass)
      
      run(rootClass, args, container)
    }
    
    fun run(rootClass: Class<*>, args: Array<String>, container: UnitContainer) {
      Config.container = container
      Config.rootClass = rootClass
      SpringApplication.run(KefisCoreServer::class.java, *args)
    }
  }
}