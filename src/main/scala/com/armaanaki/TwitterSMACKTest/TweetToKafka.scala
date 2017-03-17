package com.armaanaki.TwitterSMACKTest

import akka.actor.ActorSystem
import akka.kafka.scaladsl.Producer
import akka.kafka.{ProducerMessage, ProducerSettings}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.Done
import java.util.Date

object TweetToKafka extends App {
  if (args.length < 2) {
    println("TweetToKafka <kafka brokers> <topic>")
    System.exit(1)
  }

  val kafka_broker = args(0)
  val topic = args(1)

  val system = ActorSystem("SMACKSystem");
  implicit val materializer = ActorMaterializer.create(system)
  implicit val ec = system.dispatcher

  val producerSettings = ProducerSettings(system, new StringSerializer, new TweetSerializer)
    .withBootstrapServers(kafka_broker)

  val done = Source.single(1)
    .map { n =>
      val partition = 0
      ProducerMessage.Message(new ProducerRecord[String, Tweet](
        topic, partition, n.toString, Tweet(new Date(), "testTweet")
      ), n)
    }
    .via(Producer.flow(producerSettings))
    .map { result =>
      val record = result.message.record
      println(s"${record.topic}/${record.partition} ${result.offset}: ${record.value}" +
        s"(${result.message.passThrough})")
      result
    }
    .runWith(Sink.ignore)

  terminateWhenDone(done)



  def terminateWhenDone(result: Future[Done]): Unit = {
    result.onComplete {
      case Failure(e) =>
        system.log.error(e, e.getMessage)
        system.terminate()
      case Success(_) => system.terminate()
    }
  }
}
