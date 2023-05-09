package net.kigawa.kefis.core.server

import jakarta.servlet.http.HttpServletRequest
import net.kigawa.kefis.core.rest.EndpointPathBuilder
import net.kigawa.kefis.core.rest.RequestMethod

class EndpointInfo(
  val path: String,
  val requestMethod: Array<RequestMethod>,
) {
  
  constructor(endpointPathBuilder: EndpointPathBuilder): this(endpointPathBuilder.path, endpointPathBuilder.method)
  
  fun canDuplicate(endpointRequest: EndpointInfo): Boolean {
  }
  
  fun isMatch(request: HttpServletRequest): Boolean {
  }
}