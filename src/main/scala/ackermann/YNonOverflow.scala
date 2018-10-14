package ackermann

object StackYNonOverflow extends Test {
  case class Lazy[A](value: ()   => A) { def apply(        ) = value(  ) }
  case class X   [A](value: X[A] => A) { def apply(xa: X[A]) = value(xa) }
  def Y[A](f: Lazy[A] => Lazy[A]) = X[Lazy[A]] { x => f(x(x)) } (X[Lazy[A]]{ x => f(x(x)) })

  val ackLike: Lazy[((List[Int], Int)) => Int] => Lazy[((List[Int], Int)) => Int] = g =>
    Lazy[((List[Int], Int)) => Int] { () => x: (List[Int], Int) => x match {
      case (0 :: ms, n) => g()(              ms, n + 1)
      case (m :: ms, 0) => g()(m - 1      :: ms, 1    )
      case (m :: ms, n) => g()(m :: m - 1 :: ms, n - 1)
      case (Nil,     n) => n
    }}

  def ack(m: Int, n: Int): Int = Y(ackLike)()((m :: Nil, n))

  def main(args: Array[String]): Unit = test()
}
