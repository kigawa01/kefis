package net.kigawa.kefis.core.server

import net.kigawa.kutil.unitapi.UnitIdentify
import net.kigawa.kutil.unitapi.component.UnitContainer
import net.kigawa.kutil.unitapi.exception.NoSingleUnitException
import net.kigawa.kutil.unitapi.registrar.ClassRegistrar
import net.kigawa.kutil.unitapi.registrar.InstanceRegistrar

object UnitUtil {
  @Synchronized
  fun <T: Any> UnitContainer.getOrRegisterUnit(identify: UnitIdentify<T>): T {
    val unit = getOrNullUnit(identify)
    if (unit != null) return unit
    
    getUnit(ClassRegistrar::class.java).register(identify)
    return getUnit(identify)
  }
  
  fun <T: Any> UnitContainer.getOrCreateUnit(unitClass: Class<T>, name: String, creator: ()->T): T {
    return getOrCreateUnit(UnitIdentify(unitClass, name), creator)
  }
  
  @Synchronized
  fun <T: Any> UnitContainer.getOrCreateUnit(unitIdentify: UnitIdentify<T>, creator: ()->T): T {
    var unit = getOrNullUnit(unitIdentify)
    if (unit != null) return unit
    
    unit = creator.invoke()
    getUnit(InstanceRegistrar::class.java).register(unit)
    return unit
  }
  
  fun <T: Any> UnitContainer.getOrNullUnit(identify: UnitIdentify<T>): T? {
    val units = getUnitList(identify)
    
    if (units.isEmpty()) return null
    if (units.size == 1) return units[0]
    throw NoSingleUnitException("unit is not single count", identify = identify, units = units)
  }
}