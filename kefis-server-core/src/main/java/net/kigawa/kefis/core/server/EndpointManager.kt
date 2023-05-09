package net.kigawa.kefis.core.server

import jakarta.servlet.http.HttpServletRequest
import net.kigawa.kefis.core.rest.EndpointDef
import net.kigawa.kefis.core.rest.PathBuilder
import net.kigawa.kutil.kutil.err.ErrorHandler
import net.kigawa.kutil.kutil.reflection.KutilReflect
import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.stereotype.Service
import java.net.URI

@Service
class EndpointManager(
  private val container: UnitContainer,
  errorHandler: ErrorHandler<Exception>,
) {
  init {
    ReflectionUtil
      .classList(Config.rootClass.classLoader, errorHandler, Config.rootClass.packageName)
      .filter {KutilReflect.instanceOf(it, EndpointDef::class.java)}
  }
  
  fun findEndpoint(request: HttpServletRequest): Endpoint {
    val uri = URI(request.requestURL.toString())
    return container.getUnit(Endpoint::class.java, PathBuilder().append(uri.path).path)
  }
}