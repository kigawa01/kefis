package net.kigawa.kefis.core.server.util

import net.kigawa.kutil.kutil.err.ErrorHandler
import net.kigawa.kutil.unitapi.annotation.Inject
import net.kigawa.kutil.unitapi.exception.UnitException
import java.io.File
import java.lang.reflect.Constructor
import java.net.JarURLConnection
import java.net.URL
import java.util.*

object ReflectionUtil {
  const val JAR_PROTOCOL = "jar"
  const val FILE_PROTOCOL = "file"
  
  @Suppress("UNCHECKED_CAST")
  fun <T> crateInstance(clazz: Class<T>, values: Any?): T? {
    if (values == null) return null
    if (clazz.isInstance(values)) return values as T?
    
    val constructor = getConstructor(clazz)
  }
  
  fun getConstructor(clazz: Class<*>): Constructor<*> {
    val constructors = clazz.constructors
    if (constructors.size == 1) return constructors[0]
    for (constructor in constructors) {
      if (constructor.isAnnotationPresent(Inject::class.java)) {
        return constructor
      }
    }
    throw ConstructorException("could not get constructor", clazz)
  }
  
  fun classList(classLoader: ClassLoader, errorHandler: ErrorHandler<Exception>, packageName: String): List<Class<*>> {
    val packageDir = packageName.replace('.', '/')
    
    return classLoader.getResources(packageDir).asIterator().toList().flatMap {
      when (it.protocol) {
        JAR_PROTOCOL ->classListJar(classLoader, it, packageDir, errorHandler)
        FILE_PROTOCOL->classListFile(classLoader, File(it.file), packageName, errorHandler)
        else         -> {
          errorHandler.catch(UnitException("could not support resource protocol"))
          listOf()
        }
      }
    }
  }
  
  private fun classListJar(
    classLoader: ClassLoader,
    resource: URL,
    packageDir: String,
    errorHandler: ErrorHandler<Exception>,
  ): List<Class<*>> {
    return (resource.openConnection() as JarURLConnection).jarFile.use {jarFile->
      return@use Collections.list(jarFile.entries()).map {
        var name = it.name
        if (!name.startsWith(packageDir)) return@map null
        if (!name.endsWith(".class")) return@map null
        
        name = name.replace('/', '.').replace(".class$".toRegex(), "")
        
        return@map try {
          classLoader.loadClass(name)
        } catch (e: Exception) {
          errorHandler.catch(e)
          null
        }
        
      }.filterNotNull()
    }
  }
  
  private fun classListFile(
    classLoader: ClassLoader,
    dir: File,
    packageName: String,
    errorHandler: ErrorHandler<Exception>,
  ): List<Class<*>> {
    val files = dir.listFiles() ?: throw UnitException("cold not load unit files")
    
    return files.flatMap {file->
      
      if (file.isDirectory) {
        return@flatMap classListFile(classLoader, file, packageName + "." + file.name, errorHandler)
      }
      
      if (!file.name.endsWith(".class")) return@flatMap listOf()
      var name = file.name
      name = name.replace(".class$".toRegex(), "")
      name = "$packageName.$name"
      
      return@flatMap try {
        listOf(classLoader.loadClass(name))
      } catch (e: Exception) {
        errorHandler.catch(e)
        listOf()
      }
      
    }
  }
}