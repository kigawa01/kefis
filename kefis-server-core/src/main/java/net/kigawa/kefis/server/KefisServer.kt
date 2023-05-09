package net.kigawa.kefis.server

import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KefisServer(
  endpointRegister: EndpointRegister,
) {
  init {
    endpointRegister.registerEndpoints()
  }
  
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      run(UnitContainer.create(), args)
    }
    
    fun run(container: UnitContainer, args: Array<String>) {
      Config.container = container
      SpringApplication.run(KefisServer::class.java, *args)
    }
  }
}