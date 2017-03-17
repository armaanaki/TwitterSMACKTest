package com.armaanaki.TwitterSMACKTest

import java.util.Date

object TweetSerializerTest {
  def main(args: Array[String]) {
    val testTweet = Tweet(new Date(), "test")
    println(testTweet)
    val tweetByteArray = new TweetSerializer().serialize("tweets", testTweet)
    println(tweetByteArray.mkString(""))

    val deserializedTweet = new TweetDeserializer().deserialize("tweets", tweetByteArray)
    println(deserializedTweet)
  }
}
