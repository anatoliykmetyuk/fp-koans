package koans.weird

trait A
trait F[X]

trait Foo {
  type V
  def ===[X](implicit ev: V =:= X) = ev
}

trait Weird {
  import Types._

  type X = F25

  val foo = new Foo { type V = X }

  foo.===[X]  // Compilation time: 3 seconds
  // new Foo { type V = X }.===[X]  // Compilation time: 16 seconds
}

object Types {
  type F1  = F[A  ]
  type F2  = F[F1 ]
  type F3  = F[F2 ]
  type F4  = F[F3 ]
  type F5  = F[F4 ]
  type F6  = F[F5 ]
  type F7  = F[F6 ]
  type F8  = F[F7 ]
  type F9  = F[F8 ]
  type F10 = F[F9 ]
  type F11 = F[F10]
  type F12 = F[F11]
  type F13 = F[F12]
  type F14 = F[F13]
  type F15 = F[F14]
  type F16 = F[F15]
  type F17 = F[F16]
  type F18 = F[F17]
  type F19 = F[F18]
  type F20 = F[F19]
  type F21 = F[F20]
  type F22 = F[F21]
  type F23 = F[F22]
  type F24 = F[F23]
  type F25 = F[F24]
}