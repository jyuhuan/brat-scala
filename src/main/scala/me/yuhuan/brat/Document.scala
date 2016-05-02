package me.yuhuan.brat

import me.yuhuan.brat.annotation._
import me.yuhuan.brat.specification._
import net.liftweb.json.JsonAST._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class Document {
  var entitySpecification: EntitySpecification = null
  var eventSpecification: EventSpecification = null
  var attributeSpecification: AttributeSpecification = null

  var text: String = ""

  var entities = List[Entity]()
  var events = List[Event]()
  var attributes = List[Attribute]()

  def toJson: JObject = {
    val eventAndTriggerJSONs = events.map(_.toJson)
    val eventJSONs = eventAndTriggerJSONs.map(_._1)
    val triggerJSONs = eventAndTriggerJSONs.map(_._2)

    JObject(List(
      JField("collData", JObject(List(
        JField("entity_types", entitySpecification.toJson),
        JField("event_types", eventSpecification.toJson),
        JField("entity_attribute_types", attributeSpecification.toJson)
      ))),
      JField("docData", JObject(List(
        JField("text", JString(text)),
        JField("entities", JArray(entities.map(_.toJson))),
        JField("triggers", JArray(triggerJSONs)),
        JField("events", JArray(eventJSONs)),
        JField("attributes", JArray(attributes.map(_.toJson)))
      )))
    ))
  }

  def toHtml: String = {

    val j = toJson

    val collData = prettyRender(j \ "collData")
    val docData = prettyRender(j \ "docData")

    val bratPath = Config.BratPath


    s"""
      |<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
      |<html>
      |
      |  <head>
      |    <meta charset="UTF-8">
      |    <link rel="stylesheet" type="text/css" href="file://$bratPath/style-vis.css"/>
      |    <script type="text/javascript" src="file://$bratPath/client/lib/head.load.min.js"></script>
      |    <script>
      |      var bratLocation = 'file://$bratPath';
      |      head.js(
      |          // External libraries
      |          bratLocation + '/client/lib/jquery.min.js',
      |          bratLocation + '/client/lib/jquery.svg.min.js',
      |          bratLocation + '/client/lib/jquery.svgdom.min.js',
      |
      |          // brat helper modules
      |          bratLocation + '/client/src/configuration.js',
      |          bratLocation + '/client/src/util.js',
      |          bratLocation + '/client/src/annotation_log.js',
      |          bratLocation + '/client/lib/webfont.js',
      |
      |          // brat modules
      |          bratLocation + '/client/src/dispatcher.js',
      |          bratLocation + '/client/src/url_monitor.js',
      |          bratLocation + '/client/src/visualizer.js'
      |      );
      |
      |      var webFontURLs = [
      |          bratLocation + '/static/fonts/Astloch-Bold.ttf',
      |          bratLocation + '/static/fonts/PT_Sans-Caption-Web-Regular.ttf',
      |          bratLocation + '/static/fonts/Liberation_Sans-Regular.ttf'
      |      ];
      |
      |      head.ready(function() {
      |          Util.embed('brat-canvas', collData, docData, webFontURLs);
      |      });
      |
      |      var collData = $collData
      |
      |      var docData = $docData
      |
      |    </script>
      |  </head>
      |
      |  <body>
      |    <div id="brat-canvas"></div>
      |  </body>
      |</html>
    """.stripMargin

  }
}

object DocumentTest extends App {

  val d = new Document {
    text = "John loves the person ."

    entitySpecification = new EntitySpecification {
      entityTypes = List(
        new EntityType {
          typε = "Entity"
        }
      )
    }

    eventSpecification = new EventSpecification {
      eventTypes = List(
        new EventType {
          typε = "PmEvent"
          argumentLabels = List("Agent", "Theme")
        }
      )
    }

    attributeSpecification = new AttributeSpecification {
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

    entities = List(
      new Entity {
        id = "n1"
        typε = "Entity"
        spans = List(Span(0,4))
      },
      new Entity {
        id = "n2"
        typε = "Entity"
        spans = List(Span(11,21))
      }
    )

    events = List(
      new Event {
        id = "v1"
        typε = "PmEvent"
        spans = List(Span(5, 10))
        arguments = Map(
          "Agent" -> "n1",
          "Theme" -> "n2"
        )
      }
    )

    attributes = List(
      new Attribute {
        id = "a1"
        typε = "Positive"
        targetId = "n1"
      },
      new Attribute {
        id = "a2"
        typε = "Negative"
        targetId = "n2"
      },
      new Attribute {
        id = "a3"
        typε = "Plus"
        targetId = "v1"
      }
    )
  }

  val j = net.liftweb.json.prettyRender(d.toJson)
  Config.BratPath = "/Users/yuhuan/work/apps/brat/"
  val h = d.toHtml

  val bp = 0

}