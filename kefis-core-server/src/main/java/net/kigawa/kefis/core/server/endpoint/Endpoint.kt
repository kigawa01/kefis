package net.kigawa.kefis.core.server.endpoint

import net.kigawa.kefis.core.rest.*
import net.kigawa.kefis.core.rest.annotation.RequestBody
import net.kigawa.kefis.core.server.Request
import net.kigawa.kefis.core.server.util.ReflectionUtil
import net.kigawa.kutil.unitapi.annotation.ArgName
import net.kigawa.kutil.unitapi.component.UnitContainer
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
  
  fun call(container: UnitContainer, request: Request, json: Map<String, Any>): Any? {
    reflectMethod.parameters.map {parameter->
      
      parameter.getAnnotation(RequestBody::class.java)?.let {
        return@map getRequestBody(parameter.type, it.name, json)
      }
      
      val argName = parameter.getAnnotation(ArgName::class.java)
      return@map container.getUnit(parameter.type, argName?.name)
    }.let {
      return reflectMethod.invoke(endpointDef, *it.toTypedArray())
    }
  }
  
  fun <T: Any> getRequestBody(clazz: Class<T>, name: String, json: Map<String, Any>): T? {
    return ReflectionUtil.crateInstance(clazz, json[name])
  }
}