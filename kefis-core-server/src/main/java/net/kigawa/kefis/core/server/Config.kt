package net.kigawa.kefis.core.server

import net.kigawa.kutil.kutil.err.ErrorHandler
import net.kigawa.kutil.kutil.err.StreamErrorHandler
import net.kigawa.kutil.unitapi.component.*
import net.kigawa.kutil.unitapi.registrar.InstanceRegistrar
import org.springframework.context.ApplicationContext
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
  @Synchronized
  open fun container(applicationContext: ApplicationContext, ): UnitContainer {
    if (container.getUnitList(SpringContainerFinder::class.java).isNotEmpty()) return container
    
    container.getUnit(InstanceRegistrar::class.java).register(applicationContext)
    container.getUnit(UnitFinderComponent::class.java).add(SpringContainerFinder::class.java)
    
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