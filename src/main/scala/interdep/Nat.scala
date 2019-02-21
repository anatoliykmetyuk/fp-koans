package interop

sealed trait Nat
sealed trait Z extends Nat
sealed trait S[N <: Nat] extends Nat

trait Op[O, N <: Nat, M <: Nat] {
  type Out <: Nat
  def value(implicit ti: ToInt[Out]) = ti.value
  def print(implicit ti: ToInt[Out]) = println(value)
}

object Op {
  type Aux[O, N <: Nat, M <: Nat, R <: Nat] = Op[O, N, M] { type Out = R }

  trait OpApplyPartial[O, N <: Nat, M <: Nat] {
    def apply[NM <: Nat]()(implicit op: Aux[O, N, M, NM]): Aux[O, N, M, NM] = op
  }

  def apply[O, N <: Nat, M <: Nat] = new OpApplyPartial[O, N, M] {}
  def apply[O, N <: Nat, M <: Nat, R <: Nat]: Aux[O, N, M, R] = new Op[O, N, M] { type Out = R }
}

trait OpBuilder {
  type Tag = this.type
  type Type[N <: Nat, M <: Nat] = Op[Tag, N, M]
  type Aux [N <: Nat, M <: Nat, R <: Nat] = Op.Aux[Tag, N, M, R]

  def apply[N <: Nat, M <: Nat] = Op[Tag, N, M]
  def apply[N <: Nat, M <: Nat, R <: Nat]: Op.Aux[Tag, N, M, R] = Op[Tag, N, M, R]
}

trait ToInt[N <: Nat] { val value: Int }
object ToInt {
  def apply[N <: Nat](implicit ti: ToInt[N]) = ti
  def apply[N <: Nat](v: Int) = new ToInt[N] { val value = v }

  implicit def zero: ToInt[Z] = ToInt[Z](0)
  implicit def succ[N <: Nat: ToInt]: ToInt[S[N]] = ToInt[S[N]](ToInt[N].value + 1)
}
