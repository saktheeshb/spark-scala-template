package com.spark.analytics.app

import com.spark.analytics.util.{InputParameters, LoadDataFrame}
import org.junit.Assert
import org.scalatest.FlatSpec

class SPARKAnalyticsJobTest extends FlatSpec with HiveContext with DBSetup {


  private val SPARKAnalyticsJobTest = new SPARKAnalyticsJob(spark)
  private val InputParametersTest = InputParameters

  private val testArguments = Array("input1", "input2", "input3", "input4", "input5", "input6", "input7", "input8")
  private val nameBasics = LoadDataFrame(spark, "src/test/resources/name.basics_sample.tsv")

  "Validate input parameters" should " be properly set" in {
    InputParametersTest.setInputParameters(testArguments)
    Assert.assertEquals(testArguments.length, InputParametersTest.inputParameterLength) //ToDo: Add more testcases to validate each parameter
  }

}