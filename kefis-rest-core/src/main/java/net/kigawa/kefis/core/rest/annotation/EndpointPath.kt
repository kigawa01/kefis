package net.kigawa.kefis.core.rest.annotation

import net.kigawa.kefis.core.rest.RequestMethod

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class EndpointPath(
  val path: String,
  val method: Array<RequestMethod> = [],
)

