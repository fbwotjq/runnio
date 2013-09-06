package net.masterthought.runnio

import java.io.{File, PrintWriter}
import org.apache.commons.io.{FileUtils, IOUtils}
import org.scalatra.servlet.FileItem

object Utils {

  def copyFileToDefaultLocation(item:FileItem) : Boolean = {
    println("ContentType was: "  + item.getContentType.getOrElse("Unknown"))
    if (item.getContentType.getOrElse("").equals("application/java-archive")
        || item.getContentType.getOrElse("").equals("application/x-zip-compressed")
        || item.getContentType.getOrElse("").equals("application/octet-stream")
    ){
      FileUtils.writeByteArrayToFile(new File(item.name),item.get())
      return true
    } else false
  }

}
