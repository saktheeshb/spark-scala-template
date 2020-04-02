package com.spark.analytics.util

import java.io.{FileNotFoundException, Serializable}

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.LoggerFactory

import scala.util.Failure

object LoadDataFrame extends Serializable {

  private val logger = LoggerFactory.getLogger(this.getClass)

   def apply(spark: SparkSession, file:String): DataFrame={
    logger.info(s"loading data for file: "+file)

    var df = spark.emptyDataFrame
    try {
      df = spark.read
        .format("csv")
        .option("header", "true")
        .option("inferSchema", "true")
        .option("delimiter", "\t")
        .load(file)
    }
    catch {
      case ex: FileNotFoundException =>
        logger.error(s"File $file not found.." + ex.getStackTrace)
        Failure
      case unknown: Exception =>
        logger.error(s"Unknown exception: $unknown")
        Failure
    }
    df
  }
}
