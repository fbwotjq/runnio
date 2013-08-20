import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._
import com.earldouglas.xsbtwebplugin.PluginKeys._
import com.earldouglas.xsbtwebplugin.WebPlugin._
import sbtassembly.Plugin._
import AssemblyKeys._
import templemore.xsbt.cucumber.CucumberPlugin


object RunnioBuild extends Build {
  val Organization = "net.masterthought"
  val Name = "runnio"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.10.2"
  val ScalatraVersion = "2.2.1"
  val ourPort = 9999
  val cukeSettings = CucumberPlugin.cucumberSettings ++
      Seq(
           CucumberPlugin.cucumberFeaturesDir := file("./src/test/features"),
           CucumberPlugin.cucumberJunitReport := true,
           CucumberPlugin.cucumberJsonReport := true,
           CucumberPlugin.cucumberHtmlReport := true
      )


  lazy val project = Project (
    "runnio",
    file("."),
    settings = Defaults.defaultSettings ++ ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ cukeSettings ++Seq(
      port in container.Configuration := ourPort,
      organization := Organization,
      name := Name,
      version := Version,
      crossPaths := false,
      scalaVersion := ScalaVersion,
      resolvers += DefaultMavenRepository,
      resolvers += Classpaths.typesafeReleases,
      resolvers += Classpaths.typesafeSnapshots,
      resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
      resolvers += "repo.bodar.com" at "http://repo.bodar.com",
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.1.3",
        "org.scalatra" %% "scalatra-json" % "2.2.1",
        "org.json4s"   %% "json4s-jackson" % "3.2.4",
        "org.apache.maven.shared" % "maven-invoker" % "2.1.1",
        "org.apache.commons" % "commons-io" % "1.3.2",
        "org.apache.velocity" % "velocity" % "1.7",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "com.google.code.gson" % "gson" % "2.2.4",
        "net.masterthought" % "cucumber-reporting" % "0.0.21",
        "junit" % "junit" % "4.9" % "test",
        "info.cukes" % "cucumber-core" % "1.1.1" % "test",
        "info.cukes" % "cucumber-junit" % "1.1.1" % "test",
        "info.cukes" % "cucumber-scala" % "1.1.1" % "test",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.8.v20121106" % "container;compile",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "compile;container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}
