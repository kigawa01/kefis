package net.kigawa.kefis.core.rest.annotation

import java.lang.reflect.Parameter

class UnknownParameterException(message: String, parameter: Parameter):
  RuntimeException("$message parameter: ${parameter.name}, class: ${parameter.type.simpleName}")