package net.kigawa.kefis.core.rest

class EndpointInfo: PathBuilder() {
  
  lateinit var method: List<RequestMethod>
    private set
  
  fun append(endpointPath: EndpointPath?): EndpointInfo {
    if (endpointPath == null) return this
    
    super.append(path)
    if (endpointPath.method.isNotEmpty()) method = endpointPath.method.toList()
    return this
  }
}