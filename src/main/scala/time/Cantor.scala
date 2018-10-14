package time

import ackermann.eval._

object timeApi {
  case class X[A](value: X[A] => A) {
    def apply(xa: X[A]) = value(xa)
    def e: A = value(this)
  }

  case class R[A](cont: () => (A, R[A])) {
    lazy val result = cont()
    lazy val value  = result._1
    lazy val next   = result._2

    def map[B](f: A => B): R[B] = timeApi.cont[B] { f(value) -> next.map(f) }
  }
  def cont[A](cont: => (A, R[A])) = R[A](() => cont)
}

import timeApi._

object Cantor extends App {
  def Z[A](f: A => A): A => R[A] =
    X[A => R[A]] { x => a => cont { f(a) -> x.e(f(a)) } }.e

  def cantor(filler: Char = '_', toDropRatio: Double = 0.3, dropStartPos: Double = 0.5) =
    (s: String) => Z[List[String]] { _.flatMap {
      case str if str.startsWith(filler.toString) => str :: Nil
      case str =>
        val dropStart = (str.length * dropStartPos).toInt
        val toDrop    = (str.length * toDropRatio ).toInt

        str.take(dropStart) ::
        str.drop(dropStart).take(toDrop).map(_ => filler) ::
        str.drop(dropStart + toDrop) :: Nil
    } }(List(s)).map(_.mkString)

  val c = cantor('_', 0.1, 0.1)("X" * 150)

  println()
  Iterator.iterate(c)(_.next).take(10).map(x => println(x.value)).toList
}
