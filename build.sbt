name := "lightspark"

version := "0.1"

scalaVersion := "2.11.8"

val akkaV = "2.3.9"
val sprayV = "1.3.3"
val jacksonV = "2.4.1"

libraryDependencies ++= Seq(
  "io.spray" %% "spray-can" % sprayV
  , "io.spray" %% "spray-routing" % sprayV
  , "com.typesafe.akka" %% "akka-actor" % akkaV
  , "ch.qos.logback" % "logback-classic" % "1.1.3"
  , "com.typesafe.akka" %% "akka-slf4j" % akkaV
  , "org.apache.spark" % "spark-mllib_2.11" % "2.3.0"
  , "com.databricks" % "spark-csv_2.11" % "1.5.0"
  , "com.fasterxml.jackson.core" % "jackson-core" % jacksonV force()
  , "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonV force()
  , "com.fasterxml.jackson.core" % "jackson-databind" % jacksonV force()
  , "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % jacksonV
  , "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % jacksonV
  , "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonV
)