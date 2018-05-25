package com.detectivebag.lightspark.feature


import java.io.IOException

import com.fasterxml.jackson.databind.node.{ArrayNode, ObjectNode, ValueNode}
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.{Logger, LoggerFactory}

/*
  this is a general transformer that transform any json like object into spark acceptable dataframe like format
 */
object Transformer {


  val config: Config = ConfigFactory.load()
  val log: Logger = LoggerFactory.getLogger(getClass)

  def jsonFlatter(json: String): collection.mutable.Map[String, String] = {
    val map = collection.mutable.Map[String, String]()
    try {
      jsonFlatterHelper("", new ObjectMapper().readTree(json), map)
    }
    catch {
      case e: IOException =>
        log.error("flatten json error: ", e)
        Map[String, String]()
    }
    map
  }

  def toFeature(raw: collection.mutable.Map[String, String], arrayLength: Int = Math.pow(2, 8).toInt): Array[Double] = {
    val arr = Array.fill[Double](arrayLength)(0)

    raw.foreach(
      e => {
        arr(((e._2 + e._1).hashCode % (arrayLength - 1) + (arrayLength - 1)) % (arrayLength - 1)) = 1.0
      }
    )

    arr
  }

  def jsonFlatterHelper(currentPath: String, jsonNode: JsonNode, map: collection.mutable.Map[String, String]) {
    if (jsonNode.isObject) {
      val objectNode: ObjectNode = jsonNode.asInstanceOf[ObjectNode]
      val iter: java.util.Iterator[java.util.Map.Entry[String, JsonNode]] = objectNode.fields
      val pathPrefix: String = if (currentPath.isEmpty) ""
      else currentPath + "."
      while (iter.hasNext) {
        {
          val entry: java.util.Map.Entry[String, JsonNode] = iter.next
          jsonFlatterHelper(pathPrefix + entry.getKey, entry.getValue, map)
        }
      }
    }
    else if (jsonNode.isArray) {
      val arrayNode: ArrayNode = jsonNode.asInstanceOf[ArrayNode]
      for (i <- 0 until arrayNode.size) {
        jsonFlatterHelper(currentPath + "[" + i + "]", arrayNode.get(i), map)
      }
    }
    else if (jsonNode.isValueNode) {
      val valueNode: ValueNode = jsonNode.asInstanceOf[ValueNode]
      map.put(currentPath, valueNode.asText())
    }
  }


}
