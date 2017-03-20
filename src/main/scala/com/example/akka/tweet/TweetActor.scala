package com.example.akka.tweet

import akka.actor.{Actor, ActorLogging}

object TweetActor {
  case class Tweet(date: String, text: String)
}

class TweetActor extends Actor with ActorLogging {
  import TweetActor._

  def receive: Receive = {
    case Tweet(date, text)  => { sender ! Tweet(s"$date", s"$text") }
  }
}
