package net.masterthought.runnio.maven

import org.apache.maven.shared.invoker.InvocationOutputHandler
import org.slf4j.LoggerFactory

class CustomOutputHandler extends InvocationOutputHandler{
  val logger =  LoggerFactory.getLogger(getClass)

  var lines = List[String]()

  def consumeLine(line:String) {
    logger.info(line)
    lines = line :: lines
  }

  def getLinesAsString : String =  lines.mkString("<br/>")

}
