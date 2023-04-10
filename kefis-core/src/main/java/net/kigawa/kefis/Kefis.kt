package net.kigawa.kefis

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.File
import java.net.URL
import java.net.URLClassLoader

@SpringBootApplication
open class Kefis {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val loader = ClassLoader(
        listOf(
          File("kefis-manager\\target\\classes").toURI().toURL()
        )
      )
      val clazz = loader.loadClass("net.kigawa.kefis.manager.KefisManager")
      
      SpringApplication.run(arrayOf(Kefis::class.java,clazz), args)
    }
  }
}

class ClassLoader(list: Collection<URL>): URLClassLoader(
  Thread.currentThread().contextClassLoader.getResources("")
    .toList().toMutableSet().apply {addAll(list)}.toTypedArray()
) {
  constructor(): this(listOf<URL>())
  
  override fun loadClass(name: String?): Class<*> {
    return super.loadClass(name)
  }
}