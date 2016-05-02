package me.yuhuan.brat.annotation

import net.liftweb.json.JsonAST._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class Attribute {
  var id: String = null
  var typε: String = null
  var targetId: String = null // the thing that this attribute is labeled upon

  def toJson: JArray = {
    JArray(List(
      JString(id),
      JString(typε),
      JString(targetId)
    ))
  }
}

object AttributeTest extends App {
  val a = new Attribute {
    id = "a1"
    typε = "Pos"
    targetId = "v1"
  }

  val j = net.liftweb.json.prettyRender(a.toJson)

  val bp = 0
}

