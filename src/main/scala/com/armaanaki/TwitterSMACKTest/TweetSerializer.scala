package com.armaanaki.TwitterSMACKTest

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util.Map

import org.apache.kafka.common.serialization._;

class TweetSerializer extends Serializer[Tweet] {
  override def configure(configs: Map[String, _], isKey: Boolean): Unit= {}

  override def serialize(topic: String, data: Tweet): Array[Byte] = {
    val bos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(bos)
    oos.writeObject(data)
    oos.close

    return bos.toByteArray
  }

  override def close(): Unit = {}
}
