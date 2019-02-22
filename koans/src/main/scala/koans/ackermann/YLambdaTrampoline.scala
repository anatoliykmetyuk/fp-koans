package koans.ackermann

import eval._

object YLambdaTrampoline extends App with Test {
  case class X[A](value: X[A] => A) { def apply(xa: X[A]): A = value(xa) }
  
  def Y[A, B](f: (A => Eval[B]) => (A => Eval[B])): A => Eval[B] = {
    val g: X[A => Eval[B]] = X { x => a => later(f(x apply x)(a)) }
    g apply g
  }

  type AckStep = ((List[Int], Int)) => Eval[Int]
  val ackLike: AckStep => AckStep = g => {
    case (0 :: ms, n) => g(              ms, n + 1)
    case (m :: ms, 0) => g(m - 1      :: ms, 1    )
    case (m :: ms, n) => g(m :: m - 1 :: ms, n - 1)
    case (Nil,     n) => now(n)
  }

  def ack(m: Int, n: Int): Int = Y(ackLike)((m :: Nil, n)).compute()

  test()
}
