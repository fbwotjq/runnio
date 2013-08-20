package net.masterthought

package object runnio {

  def tuplify(x: String) = {
    if (x.indexOf("=") > 0) {
      val y = x.split("=")
      (y(0), y(1))
    }
    else (x,"true")
  }

  def splitByComma(x: String) = if (x.indexOf(",") > 0) x.split(",").toList else List[String](x)



}
