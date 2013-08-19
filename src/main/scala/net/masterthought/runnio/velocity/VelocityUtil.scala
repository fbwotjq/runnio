package net.masterthought.runnio.velocity

import org.apache.velocity.{Template, VelocityContext}
import org.apache.velocity.app.VelocityEngine
import java.util.Properties
import java.io.StringWriter

class VelocityUtil(filename:String) {

  val ve : VelocityEngine = new VelocityEngine
  var template : Template  = null
  ve.init(getProperties)
  template = ve.getTemplate(filename)
  var context : VelocityContext = null

  def generateString(context:VelocityContext) : String = {
    val stringWriter : StringWriter = new StringWriter
    this.context = context
    template.merge(context, stringWriter)
    stringWriter.close
    stringWriter.flush
    stringWriter.toString
  }

  def getProperties : Properties = {
    val props  = new Properties
    props.setProperty("resource.loader", "class")
    props.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader")
    props
  }

}
