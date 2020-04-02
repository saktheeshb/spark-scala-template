import sbt._

name := "spark-scala-template"

val jobMainClass = "com.spark.analytics.app.SPARKAnalyticsDriver"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.4.3"

val coreDependencies = Seq(
  "org.apache.spark" %% "spark-catalyst" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided"
)

val specs2Version = "3.9.1"
val scalaTestVersion = "3.0.8"
val testDependencies = Seq(
  "org.specs2" %% "specs2-core" % specs2Version % "test",
  "org.specs2" %% "specs2-junit" % specs2Version % "test",
  "org.scalactic" %%"scalactic" % scalaTestVersion % "test",
  "org.scalatest" %%"scalatest" % scalaTestVersion % "test",
  "org.specs2" %% "specs2-mock" % specs2Version % "test")

libraryDependencies ++= coreDependencies ++ testDependencies


val forkedJvmOption = Seq(
  "-server",
  "-Dfile.encoding=UTF8",
  "-Duser.timezone=GMT",
  "-Xss1m",
  "-Xms2048m",
  "-Xmx2048m",
  "-XX:+CMSClassUnloadingEnabled",
  "-XX:ReservedCodeCacheSize=256m",
  "-XX:+DoEscapeAnalysis",
  "-XX:+UseConcMarkSweepGC",
  "-XX:+UseParNewGC",
  "-XX:+UseCodeCacheFlushing",
  "-XX:+UseCompressedOops"
)

// Plugins
  enablePlugins(JavaAppPackaging)


// Plugin Settings
lazy val sbtAssemblySettings = Seq(

  // Slightly cleaner jar name
  assemblyJarName in assembly := {
    name.value + "-spark.jar"
  },

  mainClass in assembly := Some(jobMainClass),

  // Drop these jars
  assemblyExcludedJars in assembly := {
    val excludes = Set(
      "jsp-api-2.1-6.1.14.jar",
      "jsp-2.1-6.1.14.jar",
      "jasper-compiler-5.5.12.jar",
      "commons-beanutils-core-1.8.0.jar",
      "commons-beanutils-1.7.0.jar",
      "servlet-api-2.5-20081211.jar",
      "servlet-api-2.5.jar"
    )

    val cp = (fullClasspath in assembly).value
    cp filter { jar => excludes(jar.data.getName) }
  },

  assemblyMergeStrategy in assembly := {
    // case "project.clj" => MergeStrategy.discard // Leiningen build files
    case x if x.startsWith("META-INF") => MergeStrategy.discard // Bumf
    case x if x.endsWith(".html") => MergeStrategy.discard // More bumf
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }
)

// removes all jar mappings in universal and appends the fat jar
mappings in Universal := {
  val universalMappings = (mappings in Universal).value
  val fatJar = (assembly in Compile).value
  val filtered = universalMappings filter {
    case (file, str) =>  ! str.endsWith(".jar")
  }
  filtered :+ (fatJar -> ("lib/" + fatJar.getName))
}

val settings = Seq(
  // build
  name := "spark-scala-template",
  organization := "com.spark.analytics.app",
  scalaVersion := "2.11.8",
  scalaBinaryVersion := "2.11",
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint"),
  scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-target:jvm-1.8", "-feature"),
  scalacOptions in Test ++= Seq("-Yrangepos"),
  fork in run := true,
  fork in Test := true,
  fork in IntegrationTest := true,
  fork in testOnly := true,
  connectInput in run := true,
  javaOptions in run ++= forkedJvmOption,
  javaOptions in Test ++= forkedJvmOption,
  mainClass in (Compile, run) := Option(jobMainClass),

  run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated
)

lazy val main =
  project
    .in(file("."))
    .configs(IntegrationTest)
    .settings(settings: _*)
    .settings(settings: _*)
