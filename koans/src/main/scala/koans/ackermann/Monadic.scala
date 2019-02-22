package koans.ackermann

import annotation.tailrec
import pure._

object Monadic extends App with Test {
  def ackLike(m: Int, n: Int): Pure[Int] = (m, n) match {
    case (0, _) => done(n + 1)
    case (m, 0) if m > 0 => call(ackLike(m - 1, 1))
    case (m, n) if m > 0 && n > 0 =>
      for {
        inner <- call(ackLike(m, n - 1))
        outer <- call(ackLike(m - 1, inner))
      } yield outer
  }

  def ack(m: Int, n: Int): Int = ackLike(m, n).compute()

  test()
}
