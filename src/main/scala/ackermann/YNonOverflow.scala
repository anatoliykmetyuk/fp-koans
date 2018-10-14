package ackermann

import util._

object eval {
  sealed trait Eval[A] {
    @annotation.tailrec final def compute(): A = this match {
      case Now  (a) => a
      case Later(f) => f().compute()
    }
  }
  case class Later[A](value: () => Eval[A]) extends Eval[A]
  case class Now  [A](value: A            ) extends Eval[A]

  def later[A](a: => Eval[A]) = Later[A](() => a)
  def now  [A](a: A         ) = Now  [A](a      )
}

object YSingleFrame extends Test {
  import eval._

  def Y[A, B](f: (A => Eval[B]) => (A => Eval[B])): A => Eval[B] =
    a => later(f(Y(f))(a))

// later(f(Y(f))(a))
// f(Y(f))(a)
// f(b => later {f(Y(f))(b)} )(a)

  type AckStep = ((List[Int], Int)) => Eval[Int]
  val ackLike: AckStep => AckStep = g => {
    case (0 :: ms, n) => g(              ms, n + 1)
    case (m :: ms, 0) => g(m - 1      :: ms, 1    )
    case (m :: ms, n) => g(m :: m - 1 :: ms, n - 1)
    case (Nil,     n) => now(n)
  }

  def ack(m: Int, n: Int): Int =
    Y(ackLike)((m :: Nil, n)).compute()

  def main(args: Array[String]): Unit = test()
}
