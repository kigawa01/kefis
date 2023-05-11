package net.kigawa.kefis.core.server

import jakarta.servlet.http.HttpServletRequest
import net.kigawa.kefis.core.rest.PathBuilder
import net.kigawa.kefis.core.rest.RequestMethod
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import java.net.URI

@Service
class Request(
  private val applicationContext: ApplicationContext,
) {
  private var servletRequest: HttpServletRequest? = null
  fun getServletRequest(): HttpServletRequest {
    var servletRequest = this.servletRequest
    if (servletRequest != null) return servletRequest
    servletRequest = applicationContext.getBean(HttpServletRequest::class.java)
    this.servletRequest = servletRequest
    return servletRequest
  }
  
  val path: String
    get() = getServletRequest()
      .requestURL
      .toString()
      .let {URI(it)}
      .let {PathBuilder(it)}
      .path
  
  val requestMethod: RequestMethod
    get() = getServletRequest()
      .method
      .let {RequestMethod.valueOf(it)}
}