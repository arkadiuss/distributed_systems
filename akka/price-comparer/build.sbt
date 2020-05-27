name := "price-comparer"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.5",
  "com.typesafe.akka" %% "akka-stream" % "2.6.5",
  "com.typesafe.akka" %% "akka-slf4j" % "2.6.5",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "2.0.0",
  "org.xerial" % "sqlite-jdbc" % "3.31.1",
  "com.typesafe.akka" %% "akka-http"   % "10.1.12"
)
