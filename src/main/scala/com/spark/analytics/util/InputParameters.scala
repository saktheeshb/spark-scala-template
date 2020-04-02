package com.spark.analytics.util

import org.slf4j.LoggerFactory

//Todo: Convert Input Parameters to read it from a config file instead of commandline arguements
object InputParameters {

  private val logger = LoggerFactory.getLogger(this.getClass)
  val inputParameterLength = 8
  var basePath: String = _
  var nameBasicsPath: String = _

  def setInputParameters(args: Array[String]): Unit = {
    logger.info(s"Running $getClass with ${args.mkString(",")}")

    if (args.length != inputParameterLength)
      throw new IllegalArgumentException(s"""Need $inputParameterLength parameters  - productDatabase & productTable""")
    basePath = args(0)
    nameBasicsPath =  basePath+ "/" + args(1)
  }

}