package net.kigawa.kefis.server

import jakarta.servlet.http.HttpServletRequest
import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.stereotype.Service
import java.net.URI

@Service
class EndpointManager(
  private val container: UnitContainer,
) {
  init {
  
  }
  fun findEndpoint(request: HttpServletRequest): Endpoint {
    val uri = URI(request.requestURL.toString())
    return container.getUnit(Endpoint::class.java, uri.path)
  }
}