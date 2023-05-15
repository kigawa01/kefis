package net.kigawa.kefis.core.server

import net.kigawa.kutil.unitapi.UnitIdentify
import net.kigawa.kutil.unitapi.extention.UnitFinder
import net.kigawa.kutil.unitapi.options.FindOptions
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext

class SpringContainerFinder(private val context: ApplicationContext): UnitFinder {
  override fun <T: Any> findUnits(identify: UnitIdentify<T>, findOptions: FindOptions): List<T>? {
    return try {
      listOf(context.getBean(identify.unitClass))
    } catch (_: BeansException) {
      null
    }
  }
}