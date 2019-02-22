package koans.overlay

import collection.mutable.{ ListBuffer => MList }

import koans.tagged._

case class Create[E](value: E, db: MList[E])
object Create {
  implicit def create[E](implicit e: E, d: MList[E]) = Create(e, d)

  implicit def runner[E]: Runner.Aux[Create[E], Unit] =
    Runner[Create[E], Unit] { (c: Create[E]) => c.db += c.value; () }
}

case class Find[E, C](db: MList[E], cond: C)
object Find {
  implicit def find[E, C](implicit d: MList[E], c: C): Find[E, C] = Find(d, c)

  implicit def runner[E, C, V](implicit
    condRunner: Runner.Aux[C, V => Boolean]
  , extract   : Extract[E, V]
  ): Runner.Aux[Find[E, C], Option[E]] = Runner { find =>
    find.db.find { e => condRunner.run(find.cond)(extract(e)) } }
}

case class Eq[V](value: V)
object Eq {
  implicit def eqv[V](implicit v: V): Eq[V] = Eq(v)

  implicit def runner[V]: Runner.Aux[Eq[V], V => Boolean] = Runner[Eq[V], V => Boolean] {
    (eqCond: Eq[V]) => v => eqCond.value == v }
}

case class And[C1, C2](cond1: C1, cond2: C2)
object And {
  implicit def and[C1, C2](implicit c1: C1, c2: C2): And[C1, C2] = And(c1, c2)

  implicit def runner[C1, C2, V1, V2](implicit
    c1Runner: Runner.Aux[C1, V1 => Boolean]
  , c2Runner: Runner.Aux[C2, V2 => Boolean]
  ): Runner.Aux[And[C1, C2], ((V1, V2)) => Boolean] = Runner { (and: And[C1, C2]) =>
    ({ case (v1, v2) =>
      c1Runner.run(and.cond1)(v1) && c2Runner.run(and.cond2)(v2) }: ((V1, V2)) => Boolean) }
}

case class Strict[C, M](comp: C, msg: M)
object Strict {
  implicit def strict[C, M](implicit c: C, msg: M) = Strict(c, msg)

  implicit def runner[C, M, Res, Msg](implicit
    underlying: Runner.Aux[C, Option[Res]]
  , message   : Runner.Aux[M, Msg]
  ): Runner.Aux[Strict[C, M], Either[Msg, Res]] = Runner { strict =>
    underlying.run(strict.comp).toRight(message.run(strict.msg)) }
}

object IncorrectCredentialsMsg {
  implicit def self: this.type = this

  implicit def runner: Runner.Aux[this.type, String] = Runner("Incorrect credentials")
}
