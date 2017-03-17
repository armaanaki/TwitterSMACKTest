package com.armaanaki.TwitterSMACKTest

import java.io.{ByteArrayInputStream, ObjectInputStream}
import java.util.Map

import org.apache.kafka.common.serialization._;

class TweetDeserializer extends Deserializer[Tweet] {
  override def configure(configs: Map[String, _], isKey: Boolean): Unit= {}

  override def deserialize(topic: String, data: Array[Byte]): Tweet = {
    val ois = new ObjectInputStream(new ByteArrayInputStream(data))
    val tweet = ois.readObject().asInstanceOf[Tweet]
    ois.close

    return tweet
  }

  override def close(): Unit = {}
}
