package com.spark.analytics.app

import org.apache.spark.sql.SparkSession

trait DBSetup {

  val spark: SparkSession
  def setupProductTable(): Unit = {
    spark.sql("drop table if exists product.product")
    spark.sql("drop database if exists product")

    spark.sql("create database product location '/tmp/product'")
    val productData = spark.createDataFrame(Seq(
      ("000434343", "Milk"),
      ("0000010067347500", "Chocolate")
    )).toDF("product_id", "product_name")
    productData.write.mode("append").saveAsTable("product.product")
  }
}