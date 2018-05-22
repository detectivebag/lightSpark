package com.detectivebag.lightspark.util


import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/*
  to start a spark context locally for api to call
 */
object SparkUtil {
  private val mode = "local"

  private val appName = "SparkApp"

  private val sparkConf = new SparkConf().setMaster(mode).setAppName(appName)

  private val sc = new SparkContext(sparkConf)

  // to disable Spark log output to console
  // currently this is not working
  //  Logger.getLogger("org.apache.spark").setLevel(Level.OFF)

  //  private val rootLogger = Logger.getRootLogger()
  //  rootLogger.setLevel(Level.ERROR)

  private val sqlContext = new SQLContext(sc)

  def getSparkContext = sc

  def getSQLContext = sqlContext
}
