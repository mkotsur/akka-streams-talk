name := "akka-streams-talk"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.2.11"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.13"
libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.3"
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-s3" % "0.18"