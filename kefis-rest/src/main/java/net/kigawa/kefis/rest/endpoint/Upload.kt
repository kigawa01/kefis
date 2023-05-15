package net.kigawa.kefis.rest.endpoint

import net.kigawa.kefis.core.rest.EndpointDef
import net.kigawa.kefis.core.rest.annotation.EndpointPath
import net.kigawa.kefis.core.rest.annotation.RequestBody
import net.kigawa.kefis.rest.obj.FileHash

@EndpointPath(path = "/upload")
interface Upload: EndpointDef {
  @EndpointPath(path = "/file")
  fun file(@RequestBody("path") path: String, @RequestBody("hash") hash: String,
           @RequestBody("test") fileHash: FileHash): Boolean
  
  @EndpointPath(path = "/dir")
  fun dir(@RequestBody("path") path: String)
  
  @EndpointPath(path = "/delete")
  fun delete(@RequestBody("path") path: String)
}