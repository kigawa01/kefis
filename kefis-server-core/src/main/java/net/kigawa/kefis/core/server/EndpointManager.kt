package net.kigawa.kefis.core.server

import jakarta.servlet.http.HttpServletRequest
import net.kigawa.kefis.core.rest.*
import net.kigawa.kefis.core.server.UnitUtil.getOrCreateUnit
import net.kigawa.kutil.kutil.err.ErrorHandler
import net.kigawa.kutil.kutil.reflection.KutilReflect
import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.stereotype.Service
import java.lang.reflect.Method
import java.net.URI

@Service
class EndpointManager(
  private val container: UnitContainer,
  private val errorHandler: ErrorHandler<Exception>,
) {
  init {
    ReflectionUtil
      .classList(Config.rootClass.classLoader, errorHandler, Config.rootClass.packageName)
      .filter {KutilReflect.instanceOf(it, EndpointDef::class.java)}
      .forEach {
        @Suppress("UNCHECKED_CAST")
        registerEndpointDef(it as Class<out EndpointDef>)
      }
  }
  
  private fun registerEndpointDef(endpointDefClass: Class<out EndpointDef>) {
    KutilReflect
      .getAllExitMethod(endpointDefClass)
      .filter {it.isAnnotationPresent(EndpointPath::class.java)}
      .forEach {
        errorHandler.tryCatch {registerEndpointMethod(endpointDefClass, it)}
      }
  }
  
  @Synchronized
  fun registerEndpointMethod(endpointDefClass: Class<out EndpointDef>, method: Method) {
    val endpointPathBuilder = EndpointPathBuilder()
      .append(endpointDefClass.getAnnotation(EndpointPath::class.java))
      .append(method.getAnnotation(EndpointPath::class.java))
    
    val endpoint =
      container.getOrCreateUnit(Endpoint::class.java, endpointPathBuilder.path) {Endpoint(endpointPathBuilder.path)}
    
    val endpointInfo = EndpointInfo(endpointPathBuilder)
    
    if (endpoint == null) createEndpoint()
    else updateEndpoint()
  }
  
  fun findEndpoint(request: HttpServletRequest): Endpoint {
    val uri = URI(request.requestURL.toString())
    return container.getUnit(Endpoint::class.java, PathBuilder().append(uri.path).path)
  }
}