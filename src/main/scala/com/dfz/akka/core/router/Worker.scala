package com.dfz.akka.core.router

import akka.actor.Actor

/**
  * Created by prasad on 1/17/2018.
  */
class  Worker extends Actor {
  import Worker._

  def receive = {
    case msg: Work =>
      println(s"I recieved work msg and my actorRef: ${self}")
  }

}

object Worker extends App {
  case class Work()
}
