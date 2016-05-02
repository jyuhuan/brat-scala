package me.yuhuan.brat.annotation

import me.yuhuan.brat.Span
import net.liftweb.json.JsonAST._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class Entity {

  var id: String = null
  var typε: String = null
  var spans: List[Span] = null

  def toJson: JArray = {
    JArray(List(
      JString(id),
      JString(typε),
      JArray(spans.map { s =>
        JArray(List(
          JInt(s.start),
          JInt(s.end)
        ))
      })
    ))
  }
}

object EntityTest extends App {
  val n = new Entity {
    id = "n1"
    typε = "Entity"
    spans = List(
      Span(0,4),
      Span(11,21)
    )
  }
  val j = net.liftweb.json.prettyRender(n.toJson)

  val bp = 0
}