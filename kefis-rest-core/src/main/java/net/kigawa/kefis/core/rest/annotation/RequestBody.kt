package net.kigawa.kefis.core.rest.annotation

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestBody(
  val name: String,
)
