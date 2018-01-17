package com.dfz.akka.core

import akka.actor.{Props, ActorSystem, PoisonPill}

/**
  * Created by prasad on 1/17/2018.
  */
object ActorPath extends App {

  val system = ActorSystem("ActorSystem")
  val counter1 = system.actorOf(Props[Counter],"Counter")
  println(s"Actor ref for counter1 ${counter1}")

  val counterSelection1 = system.actorSelection("counter")
  println(s"Actor selection for counter1 ${counterSelection1}")

  counter1 ! PoisonPill

  Thread.sleep(100)


  val counter2 = system.actorOf(Props[Counter],"Counter")
  println(s"Actor ref for counter1 ${counter2}")

  val counterSelection2 = system.actorSelection("counter")
  println(s"Actor selection for counter1 ${counterSelection2}")

  system.terminate()

}
