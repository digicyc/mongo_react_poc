import reactivemongo.api._
import reactivemongo.bson._

import scala.concurrent.ExecutionContext.Implicits.global

object MongoConnect {
  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  
  // Connect to our database.
  val db = connection("my_db")

  def getCollection = 
      db("mycollection")
}
