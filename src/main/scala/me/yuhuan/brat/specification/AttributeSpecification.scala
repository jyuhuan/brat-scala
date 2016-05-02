package me.yuhuan.brat.specification

import net.liftweb.json.JsonAST._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class AttributeSpecification {
  class AttributeType {
    var typε: String = ""
    var glyph: String = ""

    def toJson: JObject = {
      JObject(List(
        JField("type", JString(typε)),
        JField("values", JObject(List(
          JField(typε, JObject(List(
            JField("glyph", JString(glyph))
          )))
        )))
      ))
    }
  }

  var attributeTypes = List[AttributeType]()
  def toJson: JArray = {
    JArray(attributeTypes.map(_.toJson))
  }
}

object AttributeSpecificationTest extends App {

  val as = new AttributeSpecification {
    attributeTypes = List(
      new AttributeType {
        typε = "Positive"
        glyph = "[+]"
      },
      new AttributeType {
        typε = "Negative"
        glyph = "[–]"
      },
      new AttributeType {
        typε = "Plus"
        glyph = "[∆]"
      },
      new AttributeType {
        typε = "Minus"
        glyph = "[∇]"
      }
    )
  }

  val j = net.liftweb.json.prettyRender(as.toJson)

  val bp = 0

}
