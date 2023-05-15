package net.kigawa.kefis.core.server

import net.kigawa.kefis.core.server.endpoint.EndpointStore
import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.web.bind.annotation.*

@RestController
class Controller(
  private val endpointStore: EndpointStore,
  private val request: Request,
  private val container: UnitContainer,
) {
  @RequestMapping(value = ["/**"])
  fun index(@RequestBody json: Map<String, Any>): Any? {
    return endpointStore.find(request).call(container, request, json)
  }
}