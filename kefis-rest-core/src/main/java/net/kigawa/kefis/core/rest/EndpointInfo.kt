package net.kigawa.kefis.core.rest

import net.kigawa.kefis.core.rest.annotation.EndpointPath

class EndpointInfo: PathBuilder() {
  
  var method: List<RequestMethod> = listOf()
    private set
  
  fun append(endpointPath: EndpointPath?): EndpointInfo {
    if (endpointPath == null) return this
    
    super.append(endpointPath.path)
    if (endpointPath.method.isNotEmpty()) method = endpointPath.method.toList()
    return this
  }
  
  override fun toString(): String {
    return "EndpointInfo(path=$path,method=$method)"
  }
}