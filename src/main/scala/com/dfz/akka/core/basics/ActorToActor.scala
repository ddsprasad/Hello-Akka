package com.dfz.akka.core.basics

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by prasad on 1/17/2018.
  */
object ActorToActor extends App {

  case class StartCounting(n:Int, otherActor: ActorRef)
  case class countDown(n:Int)

  class StartCountDown extends Actor {
    def receive = {
      case StartCounting(n, otherActor) =>
        println(n)
        otherActor ! countDown(n - 1)
      case countDown(n) =>
        if (n > 0) {
          println(n)
          sender ! countDown(n - 1)
        }
        else {
          context.system.terminate()
        }
    }
  }

    val system = ActorSystem("ActorSystem")
    val actor = system.actorOf(Props[StartCountDown],"StartCountDown")
    val actor1 = system.actorOf(Props[StartCountDown],"StartCountDown1")

    actor ! StartCounting(2,actor1)
  }