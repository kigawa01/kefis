package net.kigawa.kefis.core.server

import jakarta.servlet.http.HttpServletRequest

class Endpoint(
  val path: String,
) {
  fun append(endpointInfo: EndpointInfo): Endpoint {
  return this
  }
  fun request(request: HttpServletRequest) {
  }
}