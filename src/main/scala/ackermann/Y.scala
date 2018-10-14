package ackermann

object StackY extends Test {
  def Y[A, B](f: (A => B) => (A => B)): A => B =
    { a => f(Y(f))(a) }

  val ackLike: (((List[Int], Int)) => Int) => (((List[Int], Int)) => Int) = g => {
    case (0 :: ms, n) => g(              ms, n + 1)
    case (m :: ms, 0) => g(m - 1      :: ms, 1    )
    case (m :: ms, n) => g(m :: m - 1 :: ms, n - 1)
    case (Nil,     n) => n
  }

  def ack(m: Int, n: Int): Int = Y(ackLike)((m :: Nil, n))

  def main(args: Array[String]): Unit = test()
}
