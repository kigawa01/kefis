package net.kigawa.kefis.core.server

object ListUtil {
}

fun <T> Iterator<T>.toList(): MutableList<T> {
  val list = mutableListOf<T>()
  forEach(list::add)
  return list
}