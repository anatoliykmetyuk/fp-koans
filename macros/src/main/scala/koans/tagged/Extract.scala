package koans.tagged

trait Extract[From, To] { def apply(from: From): To }
object Extract {
  def apply[From, To](implicit e: Extract[From, To]) = e

  implicit def pair[From, V1, V2](implicit
    e1: Extract[From, V1]
  , e2: Extract[From, V2]
  ): Extract[From, (V1, V2)] = new Extract[From, (V1, V2)] {
    def apply(from: From): (V1, V2) = e1(from) -> e2(from)
  }
}
