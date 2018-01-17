package com.dfz.akka.core.router

import akka.actor.{Props, ActorRef, Actor}
import com.dfz.akka.core.router.Worker.Work
import scala.util
import scala.collection.concurrent.RDCSS_Descriptor

/**
  * Created by prasad on 1/17/2018.
  */
class Router extends Actor {

  var routees: List[ActorRef]= _

  override def preStart() = {
    routees = List.fill(5)(
      context.actorOf(Props[Worker])
    )
  }

  def receive() = {
    case msg: Work =>
      println("I'm a route I received a Mseg.............")
      routees(util.Random.nextInt(routees.size)) forward msg
  }

}


