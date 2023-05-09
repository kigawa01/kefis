package net.kigawa.kefis.rest.core

class EndpointPathBuilder: PathBuilder() {
  
  lateinit var method: Array<RequestMethod>
    private set
  
  fun append(endpointPath: EndpointPath?): EndpointPathBuilder {
    if (endpointPath == null) return this
    
    super.append(path)
    if (endpointPath.method.isNotEmpty()) method = endpointPath.method
    return this
  }
}