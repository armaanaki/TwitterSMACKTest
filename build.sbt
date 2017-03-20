name := "TwitterSMACKTest"
version := "0.0.1"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "0.10.2.0",
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.14",
  "org.apache.spark" % "spark-core_2.11" % "2.1.0" % "provided",
  "org.apache.spark" % "spark-streaming-kafka-0-10-assembly_2.11" % "2.1.0",
  "org.apache.spark" % "spark-streaming_2.11" % "2.1.0" % "provided",
  "com.datastax.spark" % "spark-cassandra-connector_2.11" % "2.0.0",
  "io.swagger" % "swagger-jaxrs" % "1.5.13",
  "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.9.1",
  "com.typesafe.akka" %% "akka-http" % "10.0.4",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.4",
  "ch.megard" %% "akka-http-cors" % "0.1.11",
  "org.slf4j" % "slf4j-simple" % "1.7.14"
)

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}
