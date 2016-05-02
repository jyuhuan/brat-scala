package me.yuhuan.brat.annotation

import me.yuhuan.brat.Span
import net.liftweb.json.JsonAST._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class Event {
  var id: String = ""
  var typε: String = ""
  var spans: List[Span] = null
  var arguments: Map[String, String] = null

  /**
    * In the returned value (x, y):
    *   x is the event, and
    *   y is the trigger
    *
    * @example{{{
    * var docData = {
        text: "John loves the person .",
        entities: [
          ["en1", "Entity", [[0,4]]],
          ["en2", "Entity", [[11,21]]]
        ],
        triggers: [
          ["ev1trigger", "PmEvent", [[5,10]]]
        ],
        events: [
          ["ev1", "ev1trigger", [["Agent", "en1"], ["Theme", "en2"]]]
        ],
        attributes: [
          ["a1", "Pos", "en1"],
          ["a2", "Neg", "en2"],
          ["a3", "Plus", "ev1"]
        ],
        relations: [
        ]
      }
    * }}}
    */
  def toJson: (JArray, JArray) = {
    val triggerId = s"${id}_trigger"

    val triggerNode = JArray(List(
      JString(triggerId),
      JString(typε),
      JArray(spans.map { s =>
        JArray(List(
          JInt(s.start),
          JInt(s.end)
        ))
      })
    ))

    val eventNode = JArray(List(
      JString(id),
      JString(triggerId),
      JArray(arguments.map { case (argLabel, argId) =>
        JArray(List(
          JString(argLabel),
          JString(argId)
        ))
      }.toList)
    ))

    (eventNode, triggerNode)
  }
}

object EventTest extends App {

  val v = new Event {
    id = "v1"
    typε = "PmEvent"
    spans = List(Span(5, 10))
    arguments = Map(
      "Agent" -> "en1",
      "Theme" -> "en2"
    )
  }

  val j1 = net.liftweb.json.prettyRender(v.toJson._1)
  val j2 = net.liftweb.json.prettyRender(v.toJson._2)

  val bp = 0

}