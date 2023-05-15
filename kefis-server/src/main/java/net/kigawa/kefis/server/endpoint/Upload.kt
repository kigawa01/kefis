package net.kigawa.kefis.server.endpoint

import net.kigawa.kefis.rest.endpoint.Upload

class Upload: Upload {
  override fun file(path: String, hash: String): Boolean {
    return true
  }
  
  override fun dir(path: String) {
    TODO("Not yet implemented")
  }
  
  override fun delete(path: String) {
    TODO("Not yet implemented")
  }
}