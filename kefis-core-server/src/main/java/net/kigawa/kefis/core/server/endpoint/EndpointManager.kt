package net.kigawa.kefis.core.server.endpoint

import net.kigawa.kefis.core.rest.*
import net.kigawa.kefis.core.server.Config
import net.kigawa.kefis.core.server.Request
import net.kigawa.kefis.core.server.util.ReflectionUtil
import net.kigawa.kefis.core.server.util.UnitUtil.getOrCreateUnit
import net.kigawa.kefis.core.server.util.UnitUtil.getOrRegisterUnit
import net.kigawa.kutil.kutil.err.ErrorHandler
import net.kigawa.kutil.kutil.reflection.KutilReflect
import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.stereotype.Service
import java.lang.reflect.Method

@Service
class EndpointManager(
  private val container: UnitContainer,
  private val errorHandler: ErrorHandler<Exception>,
) {
  init {
    ReflectionUtil.classList(Config.rootClass.classLoader, errorHandler, Config.rootClass.packageName)
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
    val endpointInfo = EndpointInfo()
      .append(endpointDefClass.getAnnotation(EndpointPath::class.java))
      .append(method.getAnnotation(EndpointPath::class.java))
    
    val pathEndpoint =
      container.getOrCreateUnit(PathEndpoint::class.java, endpointInfo.path) {PathEndpoint(endpointInfo.path)}
    val endpointDef = container.getOrRegisterUnit(endpointDefClass)
    
    pathEndpoint.append(endpointDef, method, endpointInfo)
  }
  
  fun findEndpoint(request: Request): Endpoint {
    return container.getUnit(PathEndpoint::class.java, request.path).request(request)
  }
}