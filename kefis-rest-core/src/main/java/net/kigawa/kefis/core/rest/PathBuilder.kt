package net.kigawa.kefis.core.rest

import java.net.URI

open class PathBuilder() {
  constructor(path: String): this() {
    append(path)
  }
  
  constructor(uri: URI): this(uri.path)
  
  lateinit var path: String
    protected set
  
  fun append(path: String?): PathBuilder {
    if (path == null) return this
    
    this.path += getPath(path)
    return this
  }
  
  private fun getPath(path: String): String {
    return path
      .let {
        if (it.endsWith("/")) it.substring(it.length - 1)
        else it
      }
      .let {
        if (it.startsWith("/")) it
        else "/$it"
      }
  }
}