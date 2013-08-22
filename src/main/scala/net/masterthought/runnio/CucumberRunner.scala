package net.masterthought.runnio

import org.slf4j.LoggerFactory

case class CucumberRunner(groupId:String, artifactId:String,version:String)

object CucumberRunnerConfig {
  val logger =  LoggerFactory.getLogger(getClass)

  var cucumberRunner : CucumberRunner = CucumberRunner("","","")

  def setTo(groupId:String, artifactId:String,versionId:String) {
    cucumberRunner = CucumberRunner(groupId,artifactId,versionId)
    logger.info("JAR DETAILS: " + cucumberRunner.groupId  + " " + cucumberRunner.artifactId + " " + cucumberRunner.version)
  }

  def isNotSet : Boolean = cucumberRunner.groupId == "" || cucumberRunner.artifactId == "" || cucumberRunner.version == ""
}
