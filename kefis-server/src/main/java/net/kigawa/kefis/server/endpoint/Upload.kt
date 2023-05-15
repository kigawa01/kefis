package net.kigawa.kefis.server.endpoint

import net.kigawa.kefis.rest.endpoint.Upload
import net.kigawa.kefis.rest.obj.FileHash

class Upload: Upload {
  override fun file(path: String, hash: String, fileHash: FileHash): Boolean {
    return true
  }
  
  override fun dir(path: String) {
    TODO("Not yet implemented")
  }
  
  override fun delete(path: String) {
    TODO("Not yet implemented")
  }
}