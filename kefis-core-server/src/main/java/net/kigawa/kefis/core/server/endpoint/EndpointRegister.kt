package net.kigawa.kefis.core.server.endpoint

import net.kigawa.kefis.core.rest.*
import net.kigawa.kefis.core.rest.annotation.EndpointPath
import net.kigawa.kefis.core.server.util.ReflectionUtil
import net.kigawa.kefis.core.server.util.UnitUtil.getOrRegisterUnit
import net.kigawa.kutil.kutil.err.ErrorHandler
import net.kigawa.kutil.kutil.reflection.KutilReflect
import net.kigawa.kutil.unitapi.component.UnitContainer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import java.lang.reflect.Method

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class EndpointRegister(
  private val container: UnitContainer,
  private val errorHandler: ErrorHandler<Exception>,
  private val endpointStore: EndpointStore,
) {
  private val logger = LoggerFactory.getLogger(EndpointRegister::class.java)
  
  fun registerEndpoint(rootClass: Class<*>) {
    ReflectionUtil.classList(rootClass.classLoader, errorHandler, rootClass.packageName)
      .filter {!it.isInterface}
      .filter {KutilReflect.instanceOf(it, EndpointDef::class.java)}
      .forEach {
        logger.info("register endpoint class: ${it.simpleName}")
        @Suppress("UNCHECKED_CAST")
        registerEndpointDef(it as Class<out EndpointDef>)
      }
  }
  
  private fun registerEndpointDef(endpointDefClass: Class<out EndpointDef>) {
    KutilReflect
      .getAllExitMethod(endpointDefClass)
      .filter {it.isAnnotationPresent(EndpointPath::class.java)}
      .forEach {
        logger.info("register endpoint method: ${it.name}")
        errorHandler.tryCatch {registerEndpointMethod(endpointDefClass, it)}
      }
  }
  
  @Synchronized
  fun registerEndpointMethod(endpointDefClass: Class<out EndpointDef>, method: Method) {
    val endpointInfo = EndpointInfo()
      .append(KutilReflect.getAllExitAnnotation(endpointDefClass, EndpointPath::class.java).firstOrNull())
      .append(method.getAnnotation(EndpointPath::class.java))
    
    logger.info("register endpoint: $endpointInfo")
    
    val endpointDef = container.getOrRegisterUnit(endpointDefClass)
    
    endpointStore.append(endpointDef, method, endpointInfo)
  }
}