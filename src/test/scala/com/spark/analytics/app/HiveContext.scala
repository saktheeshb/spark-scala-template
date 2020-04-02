package com.spark.analytics.app

import org.apache.spark.sql.SparkSession

trait HiveContext {
  val sparkBuilder = SparkSession
    .builder()
    .appName("Spark Test App")
    .master("local")
    .enableHiveSupport()

  sparkBuilder.config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
  sparkBuilder.config("spark.sql.warehouse.dir", "/tmp/metastore/hive-warehouse")
  sparkBuilder.config("hive.exec.dynamic.partition.mode", "nonstrict")

  implicit val spark: SparkSession = sparkBuilder.getOrCreate()
}

