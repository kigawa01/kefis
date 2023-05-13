package net.kigawa.kefis.core.server

import jakarta.servlet.http.HttpServletRequest
import net.kigawa.kefis.core.rest.PathBuilder
import net.kigawa.kefis.core.rest.RequestMethod
import org.springframework.stereotype.Service
import java.net.URI

@Service
class Request(
  private var servletRequest: HttpServletRequest,
) {
  
  val path: String
    get() = servletRequest
      .requestURL
      .toString()
      .let {URI(it)}
      .let {PathBuilder(it)}
      .path
  
  val requestMethod: RequestMethod
    get() = servletRequest
      .method
      .let {RequestMethod.valueOf(it)}
}