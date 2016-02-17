package tomliddle

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import com.typesafe.config.ConfigFactory
import spray.can.Http
import tomliddle.controller.ApiRoutes

object ApplicationMain extends App {

	implicit val system = ActorSystem("SpraySlickRestTemplate")

	val config = ConfigFactory.load()

	val routeService = system.actorOf(Props[ApiRoutes])

	IO(Http) ! Http.Bind(routeService, config.getString("application.server.host"), config.getInt("application.server.port"))

}