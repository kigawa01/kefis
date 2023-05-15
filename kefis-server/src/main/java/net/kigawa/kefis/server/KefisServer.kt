package net.kigawa.kefis.server

import net.kigawa.kefis.core.server.KefisCoreServer
import net.kigawa.kutil.unitapi.component.UnitContainer
import net.kigawa.kutil.unitapi.registrar.ResourceRegistrar

class KefisServer {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val container = UnitContainer.create()
      container.getUnit(ResourceRegistrar::class.java).register(KefisServer::class.java)
      
      KefisCoreServer.run(KefisServer::class.java, args, container)
    }
  }
}

