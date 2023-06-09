package net.kigawa.kefis.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KefisServer {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplication.run(KefisServer::class.java)
    }
  }
}