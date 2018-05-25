package com.detectivebag.lightspark.util

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, ObjectMapper}
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule



object JsonUtil {

  val mapper = new ObjectMapper()

  mapper.registerModule(DefaultScalaModule)

  // enable ObjectMapper support joda time
  mapper.registerModule(new JodaModule)

  // [2015,4,30,15,2,49,410] -> "2015-04-30T15:03:12.831"
  mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  //field with null value won't be show in string
  mapper.setSerializationInclusion(Include.NON_NULL)
  //  mapper.set

  def prettyPrint(obj: Any) = println("pretty print" + mapper.writeValueAsString(obj))

  def getMapper = mapper

  def sourceInclude(path: String, originNode: JsonNode) = {
    var curNode: JsonNode = originNode
    val list = path.split("\\.")
    list.map(ele => curNode = curNode.path(ele))
    curNode
  }

}
