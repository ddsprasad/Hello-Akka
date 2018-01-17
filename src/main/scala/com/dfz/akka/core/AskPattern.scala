package com.dfz.akka.core

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
/**
  * Created by prasad on 1/17/2018.
  */
object AskPattern extends App {

  case class SendName(name: String)
  case object AskName

  class AskActor(val name: String) extends Actor {
    def receive = {
      case AskName => sender ! SendName(name).name
    }
  }

  val system = ActorSystem("ActorSystem")
  val actor = system.actorOf(Props(new AskActor("dds")),"AskActor")

  implicit val timeout = Timeout(1.seconds)
  val answer = actor ? AskName
  answer.foreach(n => println("Name is "+n))

  system.terminate()



  system.terminate()

}
