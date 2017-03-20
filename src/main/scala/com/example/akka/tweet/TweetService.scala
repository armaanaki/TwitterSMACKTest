package com.example.akka.tweet

import scala.concurrent.{ExecutionContext, Future}
import com.example.akka.DefaultJsonFormats
import TweetActor._
import akka.actor.ActorRef
import akka.util.Timeout
import akka.http.scaladsl.model.Uri.Path.Segment
import akka.http.scaladsl.server.Directives
import io.swagger.annotations._
import javax.ws.rs.Path

@Api(value = "/addTweet", description = "add a tweet to smack stack", produces = "application/json")
@Path("/addTweet")
class TweetService(tweetActor: ActorRef)(implicit executionContext: ExecutionContext)
  extends Directives with DefaultJsonFormats {

  import akka.pattern.ask
  import scala.concurrent.duration._

  implicit val timeout = Timeout(2.seconds)

  import spray.json.DefaultJsonProtocol._
  implicit val tweetFormat = jsonFormat2(Tweet)

  val route = addTweet

  @ApiOperation(value = "add tweets", notes = "", nickname = "addTweet", httpMethod = "POST")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "tweet", value = "The tweet JSON you want to post", required = true,
        dataTypeClass = classOf[Tweet], paramType = "body")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return tweet", response = classOf[Tweet]),
    new ApiResponse(code = 500, message = "Internal server error")
  ))
  def addTweet =
    path("addTweet") {
      post {
        entity(as[Tweet]) { request =>
          complete { (tweetActor ? Tweet(request.date, request.text)).mapTo[Tweet] }
        }
      }
    }

}
