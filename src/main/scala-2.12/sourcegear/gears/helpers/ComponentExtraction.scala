package sourcegear.gears.helpers

import cognitro.parsers.GraphUtils.{AstPrimitiveNode, BaseNode}
import play.api.libs.json.{JsObject, JsString, JsValue}
import sdk.descriptions.{CodeComponent, Component}

import scalax.collection.edge.LkDiEdge
import scalax.collection.mutable.Graph
import sdk.descriptions.enums.ComponentEnums._
import sourcegear.gears.parsing.ParseGear

case class ModelField(propertyPath: String, value: JsValue)

object ComponentExtraction {
  implicit class ComponentWithExtractors(component: Component) {
    def extract(node: AstPrimitiveNode)(implicit graph: Graph[BaseNode, LkDiEdge], fileContents: String) : ModelField = {
      component match {
        case c: CodeComponent => {

          //@todo add some exceptions
          c.codeType match {
            case Literal=> {
              //@todo need to move this logic to the parser, specifically the key.
              val valueOption = node.properties.as[JsObject] \ "value"
              ModelField(component.propertyPath, valueOption.get)
            }
            case Token=> {
              ModelField(component.propertyPath, JsString(fileContents.substring(node.range._1, node.range._2)))
            }
          }

        }
        case _ => null
      }
    }
  }
}