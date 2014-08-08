package models

import reactivemongo.api._
import reactivemongo.bson._


case class Person(id: BSONObjectID, firstName: String, lastName: String, age: Int)

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
  
  // Not exactly sure if these are necessary
  val theBsonWriter: BSONDocumentWriter[Person] = bsonWriter
  val theBsonReader: BSONDocumentReader[Person] = bsonReader

  def toBson(obj: Person): BSONDocument = {
    BSONDocument(
      fieldId -> obj.id,
      fieldFirstname -> obj.firstName,
      fieldLastname -> obj.lastName,
      fieldAge -> obj.age
    )
  }

  def fromBson(doc: BSONDocument): Person = {
    Person(doc.getAs[BSONObjectID](fieldId).get,
      doc.getAs[String](fieldFirstname).get,
      doc.getAs[String](fieldLastname).get,
      doc.getAs[Int](fieldAge).get)
  }
}