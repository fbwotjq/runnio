package net.masterthought.runnio.maven

import org.slf4j.LoggerFactory


case class MavenConfig(mavenLocation : String)

object MavenConfigManager {

  val logger =  LoggerFactory.getLogger(getClass)

  var mavenConfig  = MavenConfig("/usr/share/maven")

  def setTo(mavenLocation : String ) {
    mavenConfig = MavenConfig(mavenLocation)
  }
}