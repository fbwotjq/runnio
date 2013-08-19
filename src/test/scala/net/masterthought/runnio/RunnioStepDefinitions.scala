package net.masterthought.runnio

import _root_.cucumber.api.scala.{ScalaDsl, EN}

class RunnioStepDefinitions extends ScalaDsl with EN{

  Given( """^a test jar$""") {
    () =>
      Console.out.println("SPECIFYING A JAR")
  }
  When( """^I upload it to my runnio$""") {
    () =>
      Console.out.println("UPLOADING OF A JAR")
  }

  Then("""^I should be able to load (\d+) tags$"""){ (arg0:Int) =>
     Console.out.println("integer")
  }

}
