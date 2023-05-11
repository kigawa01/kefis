package net.kigawa.kefis.core.server.endpoint

import net.kigawa.kefis.core.rest.*
import net.kigawa.kefis.core.server.Request
import java.lang.reflect.Method

class Endpoint internal constructor(
  val endpointDef: EndpointDef,
  val reflectMethod: Method,
  val path: String,
  val requestMethods: List<RequestMethod>,
) {
  
  internal constructor(endpointDef: EndpointDef, reflectMethod: Method, endpointInfo: EndpointInfo):
          this(endpointDef, reflectMethod, endpointInfo.path, endpointInfo.method)
  
  @Suppress("RedundantIf")
  fun isDuplicate(endpointRequest: Endpoint): Boolean {
    if (path != endpointRequest.path) return false
    if (!isDuplicateRequestMethod(endpointRequest.requestMethods)) return false
    
    return true
  }
  
  fun isDuplicateRequestMethod(methods: List<RequestMethod>): Boolean {
    if (requestMethods == methods) return true
    requestMethods.forEach {
      if (methods.contains(it)) return true
    }
    return false
  }
  
  fun call(request: Request) {
  
  }
}