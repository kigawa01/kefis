package net.kigawa.kefis.core.server.endpoint

import net.kigawa.kefis.core.rest.EndpointDef
import net.kigawa.kefis.core.rest.EndpointInfo
import net.kigawa.kefis.core.server.Request
import net.kigawa.kutil.kutil.list.KList
import net.kigawa.kutil.kutil.list.contains
import org.springframework.stereotype.Service
import java.lang.reflect.Method

@Service
class EndpointStore {
  private val endpoints = KList.create<Endpoint>()
  
  @Synchronized
  fun append(endpointDef: EndpointDef, reflectMethod: Method, endpointInfo: EndpointInfo): EndpointStore {
    val endpoint = Endpoint(endpointDef, reflectMethod, endpointInfo)
    
    if (endpoints.contains {
        it.isDuplicate(endpoint)
      }) throw EndpointDuplicateException("endpoint '${endpoint}' is duplicate")
    endpoints.add(endpoint)
    
    return this
  }
  
  fun find(request: Request): Endpoint {
    val list = endpoints
      .filter {it.path == request.path}
    list.forEach {
      if (it.requestMethods.contains(request.requestMethod)) return it
    }
    list.forEach {
      if (it.requestMethods.isEmpty()) return it
    }
    throw EndpointNotFoundException("end point not found '$request'")
  }
}