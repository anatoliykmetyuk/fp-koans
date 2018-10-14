package ackermann

object YStackOverflow extends App with Test {
  def Y[A, B](f: (A => B) => (A => B)): A => B = a => f(Y(f))(a)

  type AckStep = ((List[Int], Int)) => Int
  val ackLike: AckStep => AckStep = g => {
    case (0 :: ms, n) => g(              ms, n + 1)
    case (m :: ms, 0) => g(m - 1      :: ms, 1    )
    case (m :: ms, n) => g(m :: m - 1 :: ms, n - 1)
    case (Nil,     n) => n
  }

  def ack(m: Int, n: Int): Int = Y(ackLike)((m :: Nil, n))

  test()
}
