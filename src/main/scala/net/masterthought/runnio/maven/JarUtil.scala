package net.masterthought.runnio.maven

import org.apache.commons.io.{IOUtils => IO}
import java.util.jar.{JarEntry, JarFile}
import org.scalatra.servlet.FileItem
import net.masterthought.runnio.Utils
import scala.collection.JavaConversions._



case class Pom(groupId: String, artifactId: String, version: String)

class JarUtil {

  var pom : Pom = Pom("","","")

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
        if (jarEntry.getName.contains("pom.xml")) {
          pom = Pom(findInPom("groupId", jarFile, jarEntry), findInPom("artifactId", jarFile, jarEntry),findInPom("version", jarFile, jarEntry))
        }
      }
    } catch {
      case e: Exception => Console.out.println(e.getMessage)
    }
  }

  private def findInPom(tagName:String, jarFile: JarFile, jarEntry: JarEntry):String = {
    var lines = IO.readLines(jarFile.getInputStream(jarEntry)).toList
    lines = lines.filter(_.contains("<" + tagName + ">"))
    if (lines.size == 0) throw new RuntimeException("Tag: " + tagName + " wasn't found.")
    val result = lines.get(0).replaceAll("<" + tagName + ">","").replaceAll("</" +tagName + ">","").trim
    Console.out.println(tagName + " is: " + result)
    result
  }


}
