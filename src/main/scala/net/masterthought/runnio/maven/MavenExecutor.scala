package net.masterthought.runnio.maven

import java.io.{File, PrintWriter}
import java.util
import org.apache.velocity.VelocityContext
import scala.collection.JavaConverters._
import org.apache.maven.shared.invoker._
import util.Properties
import scala.Some
import org.slf4j.LoggerFactory
import net.masterthought.runnio.velocity.VelocityUtil
import net.masterthought.runnio.CucumberRunnerConfig

object MavenExecutor {

  val logger =  LoggerFactory.getLogger(getClass)

  val destination = "temp_pom.xml"

  var jarName = "test.jar"

  var listOfProperties: List[(String, String)] = List()

  def build(groupId: String, artifactId: String, version: String, tag:String, properties:List[(String,String)]): String = {
    val outputHandler: CustomOutputHandler = new CustomOutputHandler()
    createPomFileFor(groupId, artifactId, version)
    val request: InvocationRequest = new DefaultInvocationRequest
    prepareCleanInstall(request, outputHandler, tag, properties)
    logger.info("Executing tag: " + tag)
    execute(request,outputHandler)
  }


  def execute(request: InvocationRequest, outputHandler: CustomOutputHandler): String = {
    val invoker: Invoker = new DefaultInvoker
    try {
      invoker.setMavenHome(new File(MavenConfigManager.mavenConfig.mavenLocation))
      val result = invoker.execute(request)
      if (result.getExitCode == 0){
        "success <br />" + outputHandler.getLinesAsString
      }
      else {
        "failure with exit code: " + result.getExitCode  + "<br />" + outputHandler.getLinesAsString
      }
    } catch {
      case mie: MavenInvocationException => "MAVEN FAILURE!!! : " + mie.getMessage
    }
  }

  def installJar {
    /*
        TODO if jar name ends with test install as test-jar otherwise as normal jar.
     */
    val outputHandler: CustomOutputHandler = new CustomOutputHandler()
    val request:InvocationRequest = new DefaultInvocationRequest
    val scalaList = new scala.collection.mutable.ListBuffer[String]
    scalaList += "org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file"
    request.setGoals(scalaList.asJava)
    request.setProperties(getPomInstallationProperties)
    request.setOutputHandler(outputHandler)
    execute(request,outputHandler)
  }

  def prepareCleanInstall(request: InvocationRequest, systemOutHandler: CustomOutputHandler, tag:String, properties:List[(String,String)]) {
    request.setPomFile(new File(destination))
    val scalaList = new scala.collection.mutable.ListBuffer[String]
    scalaList += "clean install"
    request.setGoals(scalaList.asJava)
    request.setProperties(getCucumberProperties(tag, properties))
    request.setOutputHandler(systemOutHandler)
    val props = request.getProperties
    Console.println(">>>>>" + props.toString)
  }

  def getCucumberProperties(tag:String, props:List[(String,String)]): Properties = {
    val properties = new Properties
    properties.setProperty("cucumber.options", "--format json:target/cucumber.json --tags " + tag)
    for (tuple <- props){
      properties.setProperty(tuple._1, tuple._2)
    }
    properties
  }

  def getPomInstallationProperties : Properties = {
    val properties = new Properties
    properties.setProperty("file",jarName)
    properties.setProperty("groupId",CucumberRunnerConfig.cucumberRunner.groupId)
    properties.setProperty("artifactId",CucumberRunnerConfig.cucumberRunner.artifactId)
    properties.setProperty("version",CucumberRunnerConfig.cucumberRunner.version)
    properties.setProperty("classifier","tests")
    properties.setProperty("packaging","test-jar")
    properties
  }

  def createPomFileFor(groupId: String, artifactId: String, version: String) = {
    val context = new VelocityContext(Map[String, String](("groupId", groupId), ("artifactId", artifactId), ("version", version)).asJava)
    val pomString = new VelocityUtil("pom.vm").generateString(context)
    logger.info(pomString)
    Some(new PrintWriter(destination)).foreach {
      p => p.write(pomString); p.close
    }
  }


}

