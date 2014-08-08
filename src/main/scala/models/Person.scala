package models

import reactivemongo.api._
import reactivemongo.bson._
import reactivemongo.core.commands._


case class Person(id: Long, firstName: String, lastName: String, age: Int)

object Person {
  val fieldId = "id"
  val fieldFirstname = "firstName"
  val fieldLastname = "lastName"
  val fieldAge = "age"
  
  implicit object bsonWriter extends BSONDocumentWriter[Person] {
    def write(obj: Person): BSONDocument = toBson(obj: Person)
  }
  implicit object bsonReader extends BSONDocumentReader[Person] {
    def read(doc: BSONDocument): Person = fromBson(doc)
  }
  
  def toBson(obj: Person): BSONDocument = {
    BSONDocument(
      fieldId -> obj.id,
      fieldFirstname -> obj.firstName,
      fieldLastname -> obj.lastName,
      fieldAge -> obj.age
    )
  }

  def fromBson(doc: BSONDocument): Person = {
    Person(doc.getAs[Long](fieldId).get,
      doc.getAs[String](fieldFirstname).get,
      doc.getAs[String](fieldLastname).get,
      doc.getAs[Int](fieldAge).get)
  }
}
