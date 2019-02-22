package koans
package overlay

import collection.mutable.{ ListBuffer => MList }

import koans.tagged._

import db._, data._, Runner.RunnerOps

object controller {
  @derive def register: User => Create[User]

  @derive def profile: Id => Find[User, Eq[Id]]

  @derive def login: (Name, Password) =>
    Strict[Find[User, Eq[Name] And Eq[Password]], IncorrectCredentialsMsg.type]
}

object Main extends App { import controller._
  register(User(id = 1, name = "foo", password = "bar")).run()
  println(profile(1).run())
  println(login("foo", "bar").run())
  println(login("foo", "barr").run())
}
