package interop

object Plus extends OpBuilder {
  implicit def plusTerm[N <: Nat]: Aux[N, Z, N] = Plus[N, Z, N]

  implicit def plus[N <: Nat, M <: Nat, NM <: Nat](
    implicit mn: Aux[N, M, NM]
  ): Aux[N, S[M], S[NM]] = Plus[N, S[M], S[NM]]
}

object Main extends App {
  Plus[S[Z], S[S[Z]]]().print
}