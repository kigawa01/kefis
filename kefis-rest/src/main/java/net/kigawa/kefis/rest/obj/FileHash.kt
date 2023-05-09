package net.kigawa.kefis.rest.obj

import java.nio.file.Path

data class FileHash(val path: Path, val hash: String)
