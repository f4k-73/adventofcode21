name := "adventofcode2021"

version := "0.1"

scalaVersion := "2.13.6"

assembly / mainClass := Some("me.redoak.blinddate.BlinddateShuffler")


lazy val akkaVersion = "2.6.16"
lazy val AkkaHttpVersion = "10.2.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"     % akkaVersion,
  "ch.qos.logback"    % "logback-classic" % "1.2.5",
  "com.typesafe.akka" %% "akka-testkit"   % akkaVersion % Test,
  "org.scalatest"     %% "scalatest"      % "3.2.9"     % Test,
  "com.typesafe.akka" %% "akka-http"      % AkkaHttpVersion
)
