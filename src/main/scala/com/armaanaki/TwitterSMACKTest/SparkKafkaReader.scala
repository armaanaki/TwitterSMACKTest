package com.armaanaki.TwitterSMACKTest

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.{Seconds, StreamingContext}
import com.datastax.spark.connector.streaming._

object SparkKafkaReader extends App {
  if (args.length < 6) {
    println("SparkKafkaReader <kafka brokers> <topics> <cassandra host> <cassandra port> <cassandra keyspace> <cassandra table>")
    System.exit(1)
  }

  val kafka_brokers = args(0)
  val topic = args(1).split(",")
  val cassandra_host = args(2)
  val cassandra_port = args(3)
  val cassandra_keyspace = args(4)
  val cassandra_table = args(5)

  val sparkConf = new SparkConf()
    .setAppName(getClass.getName)
    .set("spark.cassandra.connection.host", cassandra_host)
    .set("spark.cassandra.connection.port", cassandra_port)
    .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")

  val kafkaProperties = Map[String, Object](
    "bootstrap.servers" -> kafka_brokers,
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[TweetDeserializer],
    "group.id" -> "use_a_seperate_group_id_for_each_stream"
  )

  val ssc = new StreamingContext(sparkConf, Seconds(1))

  val kafkaStream = KafkaUtils.createDirectStream[String, Tweet](
    ssc,
    PreferConsistent,
    Subscribe[String, Tweet](topic, kafkaProperties)
  )

  kafkaStream.print()
  //kafkaStream.map(_._value).saveToCassandra(cassandra_keyspace, cassandra_table)

  ssc.start()
  ssc.awaitTermination
  ssc.stop()
}
