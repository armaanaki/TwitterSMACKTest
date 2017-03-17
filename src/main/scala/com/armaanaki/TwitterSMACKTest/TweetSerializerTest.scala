package com.armaanaki.TwitterSMACKTest

import java.util.Date

object TweetSerializerTest {
  def main(args: Array[String]) {
    val test = Tweet(new Date(), "test")
    println(test)
    println(new TweetSerializer().serialize("tweets", test).mkString(""));
  }
}
