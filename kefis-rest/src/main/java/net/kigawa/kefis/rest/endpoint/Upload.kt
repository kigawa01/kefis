package net.kigawa.kefis.rest.endpoint

import net.kigawa.kefis.core.rest.EndpointDef
import net.kigawa.kefis.core.rest.EndpointPath

@EndpointPath(path = "/upload")
interface Upload: EndpointDef {
  @EndpointPath(path = "/file")
  fun file(path: String, hash: String): Boolean
  
  @EndpointPath(path = "/dir")
  fun dir(path: String)
  
  @EndpointPath(path = "/delete")
  fun delete(path: String)
}