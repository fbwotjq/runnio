package net.masterthought.runnio.maven

import org.apache.commons.io.{IOUtils => IO}
import java.util.jar.{JarEntry, JarFile}
import org.scalatra.servlet.FileItem
import net.masterthought.runnio.Utils


case class Pom(groupId: String, artifactId: String, version: String)

class JarUtil {

  def uploadJar(item: FileItem): Boolean = {
    if (Utils.copyFileToDefaultLocation(item)) {
      printContents(item.name)
      return true
    }
    else false
  }

  private def printContents(filename: String) {
    try {
      val jarFile: JarFile = new JarFile(filename)
      val entries = jarFile.entries()
      while (entries.hasMoreElements) {
        val jarEntry: JarEntry = entries.nextElement()
        Console.out.println(jarEntry.getName)
        if (jarEntry.getName.contains("pom.xml")) {
        }
        Console.out.println(IO.readLines(jarFile.getInputStream(jarEntry)))
      }
    } catch {
      case e: Exception => Console.out.println(e.getMessage)
    }
  }


}
