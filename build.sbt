name := "TwitterSMACKTest"
version := "0.0.1"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "0.10.2.0",
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.14",
  "org.apache.spark" % "spark-core_2.11" % "2.1.0" % "provided",
  "org.apache.spark" % "spark-streaming-kafka-0-10-assembly_2.11" % "2.1.0",
  "org.apache.spark" % "spark-streaming_2.11" % "2.1.0" % "provided",
  "com.datastax.spark" % "spark-cassandra-connector_2.11" % "2.0.0"
)

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}
