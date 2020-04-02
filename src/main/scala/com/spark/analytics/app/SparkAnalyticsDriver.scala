package com.spark.analytics.app

import java.io.Serializable

import com.spark.analytics.util.InputParameters
import org.apache.spark.sql._
import org.slf4j.LoggerFactory

import scala.util.Failure


/**
  *
  * This class contains the logic to SPARK Analytics Spark Driver
  *
  */

object SPARKAnalyticsDriver extends Serializable {

  private val logger = LoggerFactory.getLogger(this.getClass)

  var spark: SparkSession = _

  class NotFoundException(s:String) extends Exception(s){}

  def main(args: Array[String]): Unit = {

    try {
      InputParameters.setInputParameters(args)

      println(new SPARKAnalyticsJob(initializeSpark())
        .runAnalytics.collectAsList())   //Todo: Persist the result in to a Hive Table with Parque format(as data has complex datatype array)
    }
    catch {
      case ex: Exception =>
        logger.error(s"Exception received: $ex" + ex.getMessage)
        Failure
    }
  }

  private def initializeSpark(): SparkSession = {
    logger.info(s"initializing spark session...")
    SparkSession.builder.appName("Sample Application").enableHiveSupport().getOrCreate()
  }
}