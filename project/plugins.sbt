resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += DefaultMavenRepository

resolvers += "Templemore Repository" at "http://templemore.co.uk/repo"

addSbtPlugin("templemore" % "xsbt-cucumber-plugin" % "0.6.2")

addSbtPlugin("com.mojolly.scalate" % "xsbt-scalate-generator" % "0.4.2")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.1.0")

addSbtPlugin("org.scalatra.sbt" % "scalatra-sbt" % "0.3.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.9.1")
