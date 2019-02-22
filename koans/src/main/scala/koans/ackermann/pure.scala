package koans.ackermann

import annotation.tailrec

object pure {
  sealed trait Pure[A] {
    def step: Either[Pure[A], A] = this match {
      case Done(a)       => Right(a)
      case x: NonTerm[A] => Left(x match {
        case Call(t                      ) => t()
        case Cont(Done(a            ), c ) => c(a)
        case Cont(Call(t            ), c ) => Cont(t(), c)
        case Cont(Cont(p: Pure[a], c), cc) => Cont(p, (pr: a) => Cont(c(pr), cc))
      })
    }

    @tailrec final def compute(debug: Pure[A] => Unit = _ => ()): A = {
      debug(this)
      step match {
        case Right(a) => a
        case Left (p) => p.compute(debug)
      }
    }

    def flatMap[B](f: A => Pure[B]): Pure[B] = pure.flatMap(this)(f)
    def map    [B](f: A => B      ): Pure[B] = pure.map    (this)(f)
  }

  final private[this] case class Done[A](a: A) extends Pure[A]
  
  sealed private[this] trait      NonTerm[A]                              extends Pure   [A]
  final  private[this] case class Call[A   ](t: () => Pure[A]           ) extends NonTerm[A]
  final  private[this] case class Cont[A, B](p: Pure[A], c: A => Pure[B]) extends NonTerm[B]

  def done   [A   ](a: A                        ): Pure[A] = Done(a      )
  def call   [A   ](p: => Pure[A]               ): Pure[A] = Call(() => p)
  def flatMap[A, B](pa: Pure[A])(f: A => Pure[B]): Pure[B] = Cont(pa, f  )
  def map    [A, B](pa: Pure[A])(f: A =>      B ): Pure[B] = flatMap(pa)(a => done(f(a)))
}
