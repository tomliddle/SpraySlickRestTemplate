package tomliddle

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import com.typesafe.config.ConfigFactory
import spray.can.Http
import tomliddle.controller.ApiRoutes
import tomliddle.service.UserServiceActor

import scala.concurrent.ExecutionContext

object ApplicationMain extends App {

	implicit val system = ActorSystem("SpraySlickRestTemplate")

	// For now we are using the default execution context.
	// TODO: Bulkhead pattern for database EC
	implicit val ec = ExecutionContext.global

	val config = ConfigFactory.load()

	val userServiceActor = system.actorOf(Props(new UserServiceActor()))

	val routeService = system.actorOf(Props(new ApiRoutes(userServiceActor)))

	IO(Http) ! Http.Bind(routeService, config.getString("application.server.host"), config.getInt("application.server.port"))

	sys.addShutdownHook(system.shutdown())

}