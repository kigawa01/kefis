package net.kigawa.kefis.core.server

import net.kigawa.kutil.unitapi.UnitIdentify
import net.kigawa.kutil.unitapi.component.InitStack
import net.kigawa.kutil.unitapi.extention.UnitStore
import net.kigawa.kutil.unitapi.options.RegisterOptions
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext

class SpringContainerStore(private val context: ApplicationContext): UnitStore {
  override fun <T: Any> get(identify: UnitIdentify<T>): T {
    return context.getBean(identify.unitClass)
  }
  
  override fun initGetter(identify: UnitIdentify<out Any>, initStack: InitStack) {
    get(identify)
  }
  
  override fun <T: Any> initOrGet(identify: UnitIdentify<T>, initStack: InitStack): T {
    return get(identify)
  }
  
  override fun register(identify: UnitIdentify<out Any>, options: RegisterOptions): Boolean {
    return try {
      get(identify)
      true
    } catch (_: BeansException) {
      false
    }
  }
}