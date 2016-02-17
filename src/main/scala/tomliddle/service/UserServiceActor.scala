package tomliddle.service

import akka.actor.Actor
import tomliddle.controller.{GetUser, User}


class UserServiceActor extends Actor {


	def receive: Receive = {
		case GetUser => sender() ! User("Test", "test")
	}

}
