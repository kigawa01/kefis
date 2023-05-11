package net.kigawa.kefis.core.server.endpoint

import net.kigawa.kefis.core.rest.EndpointDef
import net.kigawa.kefis.core.rest.EndpointInfo
import net.kigawa.kefis.core.server.Request
import net.kigawa.kutil.kutil.list.KList
import net.kigawa.kutil.kutil.list.contains
import java.lang.reflect.Method

class PathEndpoint(
  val path: String,
) {
  private val endpoints = KList.create<Endpoint>()
  
  @Synchronized
  fun append(endpointDef: EndpointDef, reflectMethod: Method, endpointInfo: EndpointInfo): PathEndpoint {
    val endpoint = Endpoint(endpointDef, reflectMethod, endpointInfo)
    
    if (endpoints.contains {
        it.isDuplicate(endpoint)
      }) throw EndpointDuplicateException("endpoint '${endpoint}' is duplicate")
    endpoints.add(endpoint)
    
    return this
  }
  
  fun request(request: Request): Endpoint {
    endpoints.forEach {
      if (it.requestMethods.contains(request.requestMethod)) return it
    }
    endpoints.forEach {
      if (it.requestMethods.isEmpty()) return it
    }
    throw EndpointNotFoundException("end point not found '$request'")
  }
}