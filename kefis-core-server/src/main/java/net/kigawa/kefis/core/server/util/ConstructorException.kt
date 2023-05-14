package net.kigawa.kefis.core.server.util

class ConstructorException(message: String, clazz: Class<*>):
  RuntimeException("$message, 'class=${clazz.simpleName}'") {
}