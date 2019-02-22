package koans.overlay

trait Runner[For] {
  type Out
  def run(x: For): Out
}

object Runner {
  type Aux[T, R] = Runner[T] { type Out = R }
  def apply[T, O](f: T => O): Aux[T, O] = new Runner[T] {
    type Out = O
    def run(x: T) = f(x)
  }
  def apply[T, O](f: => O): Aux[T, O] = Runner[T, O] { (_: T) => f }

  implicit class RunnerOps[For, Out](x: For)(implicit runner: Aux[For, Out]) {
    def run(): Out = runner.run(x)
  }
}
