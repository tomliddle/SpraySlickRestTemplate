package tomliddle.controller

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol


object SprayJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {


	implicit val userFormat = jsonFormat2(User)

}
