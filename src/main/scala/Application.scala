import reactivemongo.api._
import reactivemongo.bson._

import scala.concurrent.ExecutionContext.Implicits.global

import models.Person
import models.Person._

object Application {
  val collection = MongoConnect.getCollection

  def getOlderPeeps = {
    val query = BSONDocument("age" -> BSONDocument("$gt" -> 27))  
    val peopleOlderThanTwentySeven =
      collection.
        find(query).
        /*
         * Indicate that the documents should be transformed into `Person`.
         * A `BSONDocumentReader[Person]` should be in the implicit scope.
         */
        cursor[Person].
        collect[List]()

    peopleOlderThanTwentySeven.map { people =>
      for(person <- people) {
        val firstName = person.firstName
        println("Found: " + firstName)
      }
    }
  }
}

