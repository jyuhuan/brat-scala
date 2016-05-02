package me.yuhuan.brat.specification

import net.liftweb.json.JsonAST._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class EventSpecification {
  class EventType {
    var typε: String = ""
    var argumentLabels = List[String]()

    def toJson: JObject = {
      JObject(List(
        JField("type", JString(typε)),
        JField("arcs", JArray(argumentLabels.map { a =>
          JObject(
            JField("type", JString(a))
          )
        }))
      ))
    }
  }

  var eventTypes = List[EventType]()
  def toJson: JArray = {
    JArray(eventTypes.map(_.toJson))
  }
}

object EventSpecificationTest extends App {
  val es = new EventSpecification {
    eventTypes = List(
      new EventType {
        typε = "PmEvent"
        argumentLabels = List("Agent", "Theme")
      }
    )
  }

  val j = net.liftweb.json.prettyRender(es.toJson)
  val bp = 0
}
