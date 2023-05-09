package net.kigawa.kefis.server

import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class Config: WebMvcConfigurer {
  companion object {
    @JvmStatic
    lateinit var container: UnitContainer
  }
  
  @Bean
  open fun container(): UnitContainer {
    return container
  }
}