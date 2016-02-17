package tomliddle

import akka.actor.ActorSystem

object ApplicationMain extends App {

	implicit val system = ActorSystem("SpraySlickRestTemplate")



}