package koans
package overlay

import collection.mutable.{ ListBuffer => MList }

import koans.tagged._


@fields(
  Id      : Int
, Name    : String
, Password: String
) object data
import data._

@model(id, name, password) case class User()

object db {
  implicit val user: MList[User] = MList.empty
}
