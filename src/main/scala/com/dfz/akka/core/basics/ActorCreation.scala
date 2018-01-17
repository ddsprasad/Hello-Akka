package com.dfz.akka.core.basics

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by prasad on 1/8/2018.
  */
//Music controller msges
object MusicController {
  sealed trait ControllerMsg
  case object Play extends ControllerMsg
  case object Stop extends ControllerMsg

  def props = Props[MusicController]
}

//Music controller

class MusicController extends Actor {
  import MusicController._

  def receive = {
    case Play =>
      println("Music Started .....")
    case Stop =>
      println("Music Stopped......")
    case _=>
      println("UnKnown")
  }

}

//Music player masges
object MusicPlayer {
  sealed trait PlayMsg
  case object StopMusic extends PlayMsg
  case object StratMusic extends PlayMsg
}

//Music player
class MusicPlayer extends Actor {
  import MusicPlayer._

  def receive = {
    case StopMusic =>
      println("I dont want to play Music")
    case StratMusic =>
      val controller = context.actorOf(MusicController.props,"Contoller")
//      controller ! Play
  }
}

object Creation extends App{
  val system = ActorSystem("Creation")
  val  player = system.actorOf(Props[MusicPlayer],"Player")

//  player ! StratMusic
//  player ! StopMusic

  system.terminate()
}






