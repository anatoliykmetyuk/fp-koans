package interop

import shapeless.Lazy

object Plus extends OpBuilder {
  implicit def plusTerm[N <: Nat]: Aux[N, Z, N] = Plus[N, Z, N]

  implicit def plus[N <: Nat, M <: Nat, NM <: Nat](
    implicit mn: Aux[N, M, NM]
  ): Aux[N, S[M], S[NM]] = Plus[N, S[M], S[NM]]
}

object Times extends OpBuilder {
  implicit def timesTerm[N <: Nat]: Aux[N, Z, Z] = Times[N, Z, Z]

  implicit def times[N <: Nat, M <: Nat, NM <: Nat, R <: Nat](implicit
    nm:      Times.Aux[N, M, NM]
  , r : Lazy[Plus .Aux[N, NM, R]]
  ): Aux[N, S[M], R] = Times[N, S[M], R]
}

object Exp extends OpBuilder {
  implicit def expTerm[N <: Nat]: Aux[N, Z, S[Z]] = Exp[N, Z, S[Z]]

  implicit def exp[N <: Nat, M <: Nat, NM <: Nat, R <: Nat](implicit
    nm:      Exp  .Aux[N, M, NM]
  , r : Lazy[Times.Aux[N, NM, R]]
  ): Aux[N, S[M], R] = Exp[N, S[M], R]
}

object Main extends App {
  Plus [Z , N2]().print  // 2
  Plus [N1, N2]().print  // 3

  Times[N4, Z ]().print  // 0
  Times[N2, N3]().print  // 6
  Times[N5, N5]().print  // 25

  Exp[N2, Z ]().print  // 1
  Exp[Z , N4]().print  // 0
  Exp[N2, N5]().print  // 32
}