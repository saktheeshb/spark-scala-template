package com.spark.analytics.app

import com.spark.analytics.util.{InputParameters, LoadDataFrame}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.slf4j.LoggerFactory

class SPARKAnalyticsJob(spark: SparkSession) extends Serializable {

  private val logger = LoggerFactory.getLogger(this.getClass)

  def runAnalytics: DataFrame = {
    logger.info(s"running analytics job...")

    //Sample Code : Remove and add your logic here
    val nameBasics: Dataset[Row] = LoadDataFrame(spark, InputParameters.nameBasicsPath)

    nameBasics
      .filter("numVotes >= 50") //Todo: No magic number, make it configurable
      .join(nameBasics, nameBasics("tconst") === nameBasics("tconst"), "inner")
      .withColumn("score", expr(s"(numVotes/ 10) * averageRating"))
      .withColumn(
        "rank",
        rank().over(Window.orderBy(desc("score")))
      )
      .drop(nameBasics("tconst"))
      .select("primaryTitle", "rank", "tconst")

  }

}