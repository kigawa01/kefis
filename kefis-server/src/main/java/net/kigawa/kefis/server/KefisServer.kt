package net.kigawa.kefis.server

import net.kigawa.kefis.core.server.KefisCoreServer

class KefisServer {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      KefisCoreServer.run(KefisServer::class.java, args)
    }
  }
}

