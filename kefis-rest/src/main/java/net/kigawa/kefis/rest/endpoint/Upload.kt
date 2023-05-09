package net.kigawa.kefis.rest.endpoint

import net.kigawa.kefis.rest.core.EndpointDef
import net.kigawa.kefis.rest.core.EndpointPath

@EndpointPath(path = "/upload")
interface Upload: EndpointDef {
  @EndpointPath(path = "/file")
  fun file(path: String, hash: String): Boolean
  
  @EndpointPath(path = "/dir")
  fun dir(path: String)
  
  @EndpointPath(path = "/delete")
  fun delete(path: String)
}