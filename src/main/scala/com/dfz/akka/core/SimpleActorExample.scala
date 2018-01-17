package com.dfz.akka.core

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by prasad on 1/17/2018.
  */
object SimpleActorExample extends App {
  class SimpleActor extends Actor {
    def receive = {
      case s: String => println(s"Its a String: $s")
      case i: Int => println(s"Its a Int: $i")
    }

    def foo = println("Normal Method")
  }

  val system = ActorSystem("ActorSystem")
  val actor = system.actorOf(Props[SimpleActor],"SimpleActor")

  actor ! "Hi There"
  actor ! 42

  system.terminate()
}
