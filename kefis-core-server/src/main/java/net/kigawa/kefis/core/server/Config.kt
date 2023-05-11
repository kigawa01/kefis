package net.kigawa.kefis.core.server

import jakarta.servlet.http.HttpServletRequest
import net.kigawa.kutil.kutil.err.ErrorHandler
import net.kigawa.kutil.kutil.err.StreamErrorHandler
import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class Config: WebMvcConfigurer {
  companion object {
    lateinit var container: UnitContainer
    lateinit var rootClass: Class<*>
  }
  
  @Bean
  open fun container(): UnitContainer {
    return container
  }
  
  @Bean
  open fun errorHandler(): ErrorHandler<Exception> {
    return StreamErrorHandler(Exception::class.java)
  }
  
//  @Bean
//  open fun request(httpServletRequest: HttpServletRequest): Request {
//    return Request()
//  }
}