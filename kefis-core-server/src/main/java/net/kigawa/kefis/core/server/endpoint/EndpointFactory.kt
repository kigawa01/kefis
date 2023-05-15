package net.kigawa.kefis.core.server.endpoint

import net.kigawa.kefis.core.rest.annotation.IgnoreField
import net.kigawa.kefis.core.server.util.ReflectionUtil
import net.kigawa.kutil.kutil.reflection.KutilReflect
import net.kigawa.kutil.unitapi.annotation.ArgName
import net.kigawa.kutil.unitapi.component.UnitContainer
import org.springframework.stereotype.Service

@Service
class EndpointFactory(private val container: UnitContainer) {
  @Suppress("UNCHECKED_CAST")
  fun <T> crateInstance(clazz: Class<T>, values: Any?): T? {
    if (values == null) return null
    if (clazz.isInstance(values)) return values as T?
    
    val constructor = ReflectionUtil.getConstructor(clazz)
    
    val instance = constructor
      .parameters
      .map {
        val argName = it.getAnnotation(ArgName::class.java)
        return@map container.getUnit(it.type, argName?.name)
      }
      .toTypedArray()
      .let {constructor.newInstance(*it)}
    
    values as Map<String, Any>
    KutilReflect.getAllExitFields(clazz).forEach {
      if (it.isAnnotationPresent(IgnoreField::class.java)) return@forEach
      it.isAccessible = true
      it.set(instance, crateInstance(it.type, values[it.name]))
    }
    
    return instance
  }
}