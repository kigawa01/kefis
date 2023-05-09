package net.kigawa.kefis.core.rest

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class EndpointPath(
  val path: String,
  val method: Array<RequestMethod> = [],
)

