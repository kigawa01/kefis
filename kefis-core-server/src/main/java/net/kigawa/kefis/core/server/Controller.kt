package net.kigawa.kefis.core.server

import net.kigawa.kefis.core.server.endpoint.EndpointManager
import org.springframework.web.bind.annotation.*

@RestController
class Controller(
  private val endpointManager: EndpointManager,
  private val request: Request,
) {
//  @RequestMapping(value = ["/**"])
//  fun index(request: Request, obj: Any): Any {
//    return endpointManager.findEndpoint(request).call(request)
//  }
  
  @RequestMapping(value = ["/"])
  fun index(@RequestBody json: Map<String, Any>): Any {
    return endpointManager.findEndpoint(request).call(request, json)
  }
}