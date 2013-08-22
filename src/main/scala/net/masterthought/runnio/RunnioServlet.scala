package net.masterthought.runnio

import cucumber.{CucumberContentFactory}
import maven.{MavenConfigManager, MavenContentFactory, MavenExecutor, JarUtil}
import org.scalatra._
import javax.servlet.annotation.MultipartConfig
import servlet.{FileItem, FileUploadSupport}
import org.slf4j.LoggerFactory
import java.io.File
import net.masterthought.cucumber.ReportBuilder
import scala.collection.JavaConverters._
import org.apache.commons.io.FileUtils


@MultipartConfig(maxFileSize = 1024 * 1024 * 1024)
class RunnioServlet extends RunnioStack with FileUploadSupport {

  val logger = LoggerFactory.getLogger(getClass)

  val jarUtil = new JarUtil()

  get("/") {
    contentType = "text/html"
    ssp("/main", "content" -> ContentFactory.dashboard)
  }

  post("/config-set") {
    val group = params.getOrElse("groupId", halt(404, <h1>
      No groupId
    </h1>))
    val artifact = params.getOrElse("artifactId", halt(404, <h1>
      No artifactId
    </h1>))
    val version = params.getOrElse("version", halt(404, <h1>
      No version
    </h1>))

    val testJar = params.get("testJar") != None

    if(testJar) {
      logger.info("It is a Test Jar!")
      CucumberRunnerConfig.setTo(group, artifact, version)
    }

    MavenExecutor.installJar(testJar)
    redirect("/config-success")
  }

  get("/upload") {
    contentType = "text/html"
    ssp("/main", "content" -> JarContentFactory.uploadJar)
  }

  post("/upload") {
    val item: FileItem = fileParams.get("document").getOrElse(halt(500))
    contentType = "text/html"
    if (jarUtil.uploadJar(item)) {
      MavenExecutor.jarName = item.name
      ssp("/main", "content" -> JarContentFactory.successfulUpload(item,jarUtil.pom.groupId,jarUtil.pom.artifactId, jarUtil.pom.version))
    }
    else {
      ssp("/main", "content" -> "<i>not a jar</i><br/><a href=\"/upload\">Try again!</a>")
    }
  }

  get("/config-success") {
    val config = CucumberRunnerConfig.cucumberRunner.groupId + " " + CucumberRunnerConfig.cucumberRunner.artifactId + " " + CucumberRunnerConfig.cucumberRunner.version
    contentType = "text/html"
    ssp("/main", "content" -> JarContentFactory.jarConfigSuccess(config))
  }

  get("/editor") {
    contentType = "text/html"
    redirect("/index.html")
  }

  get("/specs/:tag") {
    var tag = params.getOrElse("tag", halt(404, <h1>You made a mistake ;-)</h1>))

    if (tag.indexOf("@") != 0) {
      tag = "@" + tag
    }
    logger.info("JAR DETAILS: " + CucumberRunnerConfig.cucumberRunner.groupId + " " + CucumberRunnerConfig.cucumberRunner.artifactId + " " + CucumberRunnerConfig.cucumberRunner.version)

    val string = MavenExecutor.build(
      CucumberRunnerConfig.cucumberRunner.groupId,
      CucumberRunnerConfig.cucumberRunner.artifactId,
      CucumberRunnerConfig.cucumberRunner.version,
      tag, MavenExecutor.listOfProperties
    )
    contentType = "text/html"
    ssp("/main","content" -> CucumberContentFactory.executionResult(tag,string,generateWithDefaultPaths))
  }


  get("/run-spec") {
    contentType = "text/html"
    ssp("/main","content" -> CucumberContentFactory.executeSpec(CucumberRunnerConfig.isNotSet))
  }

  get("/maven-change") {
    contentType = "text/html"
    ssp("/main","content" -> MavenContentFactory.changeMavenLocation)
  }

  post("/maven-change-location") {
    val location = params.getOrElse("maven", halt(404, <h1>
      No maven location defined
    </h1>))
    MavenConfigManager.setTo(location)
    redirect("/maven-config-success")
  }

  get("/maven-config-success") {
    val config = MavenConfigManager.mavenConfig.mavenLocation
    contentType = "text/html"
    ssp("/main", "content" -> MavenContentFactory.mavenLocationChangeResult(config))

  }

  post("/result") {
    val properties = params.getOrElse("properties", null);
    Console.out.println("System Properties: " + properties)
    MavenExecutor.listOfProperties = splitByComma(properties).map(tuplify(_))
    redirect("/specs/" + params.get("tag").get)
  }

  var defaultReportsLocation = "."
  var defaultJsonLocation = "target/cucumber.json"

  def generateWithDefaultPaths : String = generate(defaultReportsLocation, defaultJsonLocation)

  def generate(reportsLocation:String, jsonLocation : String) :String = {
    val rd = new File(reportsLocation)

    if (!rd.exists()) {
      rd.mkdirs();
    }

    val list = new scala.collection.mutable.ListBuffer[String]
    list += jsonLocation
    val reportBuilder = new ReportBuilder(list.asJava, rd, "","Local","cucumber-jvm",false,false,true,false,false,"",false)
    reportBuilder.generateReports()
    """file://""" + rd.getAbsolutePath.replace(".","/") + "feature-overview.html"
  }

}
