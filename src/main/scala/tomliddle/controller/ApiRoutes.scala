package tomliddle.controller

import akka.actor.{ActorLogging, ActorRef, Actor}
import spray.http.StatusCodes
import spray.routing._
import akka.pattern.ask
import spray.util.LoggingContext
import akka.util.Timeout
import scala.concurrent.{ExecutionContext}
import scala.concurrent.duration._
import scala.util.control.NonFatal
import JsonSupport._

case class User(userId: String, email: String)

case class GetUser(userId: String)
case class PutUser(user: User)
case class DeleteUser(userId: String)



class ApiRoutes(userServiceActor: ActorRef)(implicit ec: ExecutionContext) extends Actor with HttpService with ActorLogging {

	implicit val timeout = Timeout(2.seconds)
	implicit def actorRefFactory = context


	implicit val handler = ExceptionHandler {
		case NonFatal(e) => ctx => {
			log.error("Exception in routed HTTP service", e)
			ctx.complete(StatusCodes.InternalServerError)
		}
		case _  => ctx => {
			ctx.complete(StatusCodes.InternalServerError)
		}
	}

	val userCRUDRoute =
		path("user" / Segment ~ PathEnd) { identifier: String =>
			get {
				val fut = (userServiceActor ? GetUser(identifier)).mapTo[User]
				onSuccess(fut) {
					complete(_)
				}
			} ~
			put {
				entity(as[User]) { user =>
					val fut = (userServiceActor ? PutUser(user)).mapTo[User]
					onSuccess(fut) {
						complete(_)
					}
				}
			} ~
			delete {
				complete((userServiceActor ? DeleteUser(identifier)).mapTo[User].map(u => u))
			}
		}


	def receive: Receive =
		runRoute(userCRUDRoute)(handler, RejectionHandler.Default, context, RoutingSettings.default, LoggingContext.fromActorRefFactory)

}
