import reactivemongo.api._
import reactivemongo.bson._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

import models.MongoConnect
import models.Person
import models.Person._

object Application extends {
  // Our Person collection
  val collection = MongoConnect.collectionPerson

  def addPerson = {
    val person = Person(22L, "John", "Doe", 33)
    collection.insert(person)
  }

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

  def main(args: Array[String]): Unit = {
    // add a person
    println("Add a person to the collection")
    val personF = addPerson
    
    // Once added and we get a response of it's success, 
    // then query for people older then 27
    personF onComplete {
      case Failure(e) => throw e
      case Success(msg) => {
        println("Get people older then 27")
        getOlderPeeps
      }
    }
  }
}

