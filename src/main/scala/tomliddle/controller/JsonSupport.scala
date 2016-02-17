package tomliddle.controller

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol


object JsonSupport extends DefaultJsonProtocol with SprayJsonSupport {


	implicit val userFormat = jsonFormat2(User)


}
