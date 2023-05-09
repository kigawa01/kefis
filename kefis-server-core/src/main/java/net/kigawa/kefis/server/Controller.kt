package net.kigawa.kefis.server

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
  private val endpointManager: EndpointManager,
) {
  @RequestMapping(value = ["/**"])
  fun index(request: HttpServletRequest) {
    val endpoint = endpointManager.findEndpoint(request)
    endpoint.request(request)
  }
}