package net.kigawa.kefis.core.server

import net.kigawa.kefis.core.server.endpoint.EndpointManager
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
  private val endpointManager: EndpointManager,
) {
//  @RequestMapping(value = ["/**"])
//  fun index(request: Request, obj: Any): Any {
//    return endpointManager.findEndpoint(request).call(request)
//  }
  
  @RequestMapping(value = ["/"])
  fun index(request: Request): Any {
    Thread
      .currentThread()
      .stackTrace
      .forEach(::println)
    return endpointManager.findEndpoint(request).call(request)
  }
}