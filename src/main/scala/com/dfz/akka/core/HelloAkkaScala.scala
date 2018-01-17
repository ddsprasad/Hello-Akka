package com.dfz.akka.core

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by prasad on 1/8/2018.
  */

//define actor message
case class WhoToGreet(who: String)

//define greeter actor
class Greeter extends Actor{
  def receive = {
    case WhoToGreet(who) => println(s"Hello $who")
  }
}

object HelloAkkaScala extends App{
  //create the 'hello akka' actor system
  val system = ActorSystem("Hello-Akka")
  //craete the 'greeter actor'
  val greeter = system.actorOf(Props[Greeter],"greeter")
//send whotogreet messege to actor
  greeter ! WhoToGreet("Akka")
}
