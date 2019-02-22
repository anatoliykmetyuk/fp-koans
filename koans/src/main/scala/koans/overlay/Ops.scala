package koans.overlay

trait Create[E] { def run(): Unit }
object Create {
  def apply[E](implicit c: Create[E]) = c

  implicit def create[E](implicit e: E, c: Collection[E]): Create[E] =
    () => c insert e
}

trait Collection[E] {
  def insert(e: E): Unit
  def find[V: Extract[E, ?]](cond: Condition[V]): Option[E]
}
object Collection {
  def apply[E](implicit c: Collection[E]) = c
}

trait Find[E, C] { def run(): Option[E] }
object Find {
  def apply[E, C](implicit f: Find[E, C]) = f

  implicit def find[E: Collection, V: Extract[E, ?], C[_] <: Condition[_]](implicit cond: C[V])
  : Find[E, C[V]] = () => Collection[E].find(cond.asInstanceOf[Condition[V]])
}

trait Condition[V] { def check(v: V): Boolean }
trait Eq[V] extends Condition[V] {
  val target: V
  def check(v: V) = v == target
}
object Eq {
  implicit def eq[X](implicit x: X): Eq[X] = new Eq[X] { val target = x }
}

trait Extract[From, To] { def apply(from: From): To }
object Extract {
  def apply[From, To](implicit e: Extract[From, To]) = e
}
