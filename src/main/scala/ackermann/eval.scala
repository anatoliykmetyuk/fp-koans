package ackermann

object eval {
  sealed trait Eval[A] {
    @annotation.tailrec final def compute(): A = this match {
      case Now  (a) => a
      case Later(f) => f().compute()
    }
  }
  private[this] final case class Later[A](value: () => Eval[A]) extends Eval[A]
  private[this] final case class Now  [A](value: A            ) extends Eval[A]

  def later[A](a: => Eval[A]): Eval[A] = Later[A](() => a)
  def now  [A](a: A         ): Eval[A] = Now  [A](a      )
}
