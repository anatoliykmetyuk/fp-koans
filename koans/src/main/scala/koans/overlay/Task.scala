package koans.overlay

case class User(id: Option[Int] = None, name: String, password: String)

object db {
  var user: List[User] = Nil
}

object assumptions {
  case class Id(value: Int)

  implicit def int2id(x: Int): Id = Id(x)
  implicit def extract: Extract[User, Id] = _.id.get

  implicit def userC: Collection[User] = new Collection[User] {
    def insert(u: User): Unit = db.user :+= u
    override def find[V: Extract[User, ?]](c: Condition[V]): Option[User] =
      db.user.find { u =>
        val e: V = Extract[User, V].apply(u)
        c.check(e)
      }
  }
}

object controller { import assumptions._

  def register(implicit e1: User): Create[User] = implicitly
  def profile(implicit id: Id): Find[User, Eq[Id]] = implicitly
  // def login(implicit name: Name, password: Password)
  // : Strict[Find[User, Eq[Name] And Eq[Password]], BadCredentialsMsg] = implicitly

}

object Main extends App { import controller._
  // register(User(id = Some(1), name = "foo", password = "bar")).run()
  // println(profile(1).run())
  // login("foo", "bar")
}
