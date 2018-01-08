package com.dfz.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.dfz.akka.Checker.{BlackUser, CheckUser}
import com.dfz.akka.Storage.AddUser

import scala.concurrent.duration._

/**
  * Created by prasad on 1/8/2018.
  */

case class  User(username: String, email: String)

object Recorder {
  sealed trait RecorderMsg
//Recorder Msges
  case class NewUser(user: User) extends RecorderMsg

  def props(checker: ActorRef, storage: ActorRef) =
  Props(new Recorder(checker, storage))
}

object Checker {
  sealed trait CheckerMsg
  //Cheker msgs
  case class CheckUser(user: User) extends CheckerMsg

  sealed trait CheckerResponse
  //Chekcer responses
  case class BlackUser(user: User) extends CheckerResponse
  case class WhiteUser(user: User) extends CheckerResponse
}

object Storage {
  sealed trait StorgeMsg
  //Storage msges
  case class AddUser(user: User) extends StorgeMsg
}

class Storage extends Actor {
  var users = List.empty[User]
  def receive = {
    case AddUser(user) =>
      println(s"Storage: Added User $user")
      users = user::users
        }
}

class Checker extends Actor {
  val blackList = List(
    User("Adam","adam@gamil.com")
  )
  def receive = {
    case CheckUser(user) if blackList.contains(user) =>
      println(s"Checker: $user is in the BlackList")
      sender() ! BlackUser(user)
    case CheckUser(user) =>
      println(s"Chekcer: $user not in the BlackList")
  }
}

class Recorder(checker: ActorRef, storage: ActorRef) extends Actor {
  import Recorder._

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val timeout = Timeout(5 seconds)

  def receive = {
    case NewUser(user) =>
      println(s"Recorder receives NewUser for ${user}")
      checker ? Checker.CheckUser(user) map {
        case Checker.WhiteUser(user) =>
          storage ! Storage.AddUser(user)
        case Checker.BlackUser(user) =>
          println(s"Recorder: ${user} in in black user.")
      }
  }
}

object TalkToActor extends App {

  //creatae actor system
  val system = ActorSystem("talk-to-actor")
  //create chekcer actor
  val checker = system.actorOf(Props[Checker],"chekcer")
  //create storage actor
  val storage = system.actorOf(Props[Storage],"storage")
  //create recorder actor
  val recorder = system.actorOf(Recorder.props(checker,storage),"recorder")

  recorder ! Recorder.NewUser(User("Adam","adam@gamil.com"))

  Thread.sleep(100)

  system.terminate()


}

