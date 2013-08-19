package net.masterthought.runnio

import cucumber.CucumberContentFactory
import maven.{MavenConfigManager, MavenContentFactory, MavenExecutor, JarUtil}
import org.scalatra._
import javax.servlet.annotation.MultipartConfig
import servlet.{FileItem, FileUploadSupport}
import org.slf4j.LoggerFactory

@MultipartConfig(maxFileSize = 1024 * 1024 * 1024)
class RunnioServlet extends RunnioStack with FileUploadSupport {

  val logger = LoggerFactory.getLogger(getClass)

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
    CucumberRunnerConfig.setTo(group, artifact, version)
    MavenExecutor.installJar
    redirect("/config-success")
  }

  get("/upload") {
    contentType = "text/html"
    ssp("/main", "content" -> JarContentFactory.uploadJar)
  }

  post("/upload") {
    val item: FileItem = fileParams.get("document").getOrElse(halt(500))
    contentType = "text/html"
    if (new JarUtil().uploadJar(item)) {
      MavenExecutor.jarName = item.name
      ssp("/main", "content" -> JarContentFactory.successfulUpload(item))
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
    ssp("/main","content" -> CucumberContentFactory.executionResult(tag,string))
  }


  get("/run-spec") {
    contentType = "text/html"
    ssp("/main","content" -> CucumberContentFactory.executeSpec)
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

}
