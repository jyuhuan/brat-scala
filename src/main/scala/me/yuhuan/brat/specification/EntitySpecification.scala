package me.yuhuan.brat.specification

import net.liftweb.json.JsonAST._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class EntitySpecification {
  class EntityType {
    var typε: String = ""
    var labels = List[String](typε)
    var bgColor: String = "#FFFFFF" // default color
    var borderColor: String = "darken" // default

    def toJson: JObject = {
      new JObject(List(
        JField("type", JString(typε)),
        JField("labels", JArray(labels.map(JString))),
        JField("bgColor", JString(bgColor)),
        JField("borderColor", JString(borderColor))
      ))
    }
  }

  var entityTypes = List[EntityType]()

  def toJson: JArray = {
    JArray(entityTypes.map(_.toJson))
  }
}

object EntitySpecificationTest extends App {
  val es = new EntitySpecification {
    entityTypes = List(
      new EntityType {
        typε = "Entity"
        labels = List("Entity", "Et")
        bgColor = "#CCC"
        borderColor = "darken"
      },
      new EntityType {
        typε = "Event"
        labels = List("Event", "Ev")
        bgColor = "#ABC"
        borderColor = "darken"
      }
    )
  }

  val json = net.liftweb.json.prettyRender(es.toJson)

  val bp = 0
}
