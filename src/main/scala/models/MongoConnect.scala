package models

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

import reactivemongo.api.MongoDriver
import reactivemongo.api.DefaultDB
import reactivemongo.api.MongoConnection
import reactivemongo.api.collections.default.BSONCollection

  
object MongoConnect {
  val uri = "mongodb://localhost:27017/mongotest"
  val driver: MongoDriver = new MongoDriver
  val databaseName = "mongotest"

  lazy val connection =
    MongoConnection.parseURI(uri).map { parsedUri =>
      driver.connection(parsedUri)
    }

  lazy val connDbName = connection.get.db(databaseName)

  def getCollection(db: DefaultDB, collName: String): BSONCollection = {
    db.collection(collName)
  }

  lazy val collectionPerson = getCollection(connDbName, "person")
  
}
