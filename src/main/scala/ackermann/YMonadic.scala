package ackermann

import pure._

object YMonadic extends App with Test {
  def Y[A, B](f: (A => Pure[B]) => (A => Pure[B])): A => Pure[B] =
    a => call(f(Y(f))(a))

  type AckStep = ((Int, Int)) => Pure[Int]
  val ackLike: AckStep => AckStep = g => {
    case (0, n) => done(n + 1)
    case (m, 0) => g(m - 1, 1)
    case (m, n) =>
      for {
        inner <- g(m, n - 1    )
        outer <- g(m - 1, inner)
      } yield outer
  }

  def ack(m: Int, n: Int): Int =
    Y(ackLike)((m, n)).compute()

  test()
}
