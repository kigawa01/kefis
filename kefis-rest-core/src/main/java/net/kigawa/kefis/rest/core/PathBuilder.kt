package net.kigawa.kefis.rest.core

open class PathBuilder {
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