package net.masterthought.runnio

import java.io.PrintWriter
import org.apache.commons.io.IOUtils
import org.scalatra.servlet.FileItem

object Utils {

  def copyFileToDefaultLocation(item:FileItem) : Boolean = {
    println("ContentType was: "  + item.getContentType.getOrElse("Unknown"))
    if (item.getContentType.getOrElse("").equals("application/java-archive")){
    Some(new PrintWriter(item.name)).foreach {
      p => p.write(IOUtils.toString(item.getInputStream)); p.close
    }
      return true
    } else false
  }

}
