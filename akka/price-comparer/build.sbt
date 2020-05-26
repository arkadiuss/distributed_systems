name := "price-comparer"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.5",
  "com.typesafe.akka" %% "akka-stream" % "2.6.5",
  "com.typesafe.akka" %% "akka-slf4j" % "2.6.5",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)
