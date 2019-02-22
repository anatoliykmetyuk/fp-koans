package koans.tagged

trait Tagged[Tag, Type] {
  val value: Type

  override def equals(that: Any): Boolean = that match {
    case x: Tagged[_, _] => value.equals(x.value)
    case x => value.equals(x)
  }

  override def hashCode: Int = value.hashCode

  override def toString: String = s"Tagged($value)"
}

trait TaggedBuilder[T] {
  type Tag  = this.type
  type Type = Tagged[Tag, T]
  type Data = T

  def apply[E](t: T): Type = new Tagged[Tag, T] { val value = t }

  implicit def to  (x: T   ): Type = apply(x)
  implicit def from(x: Type): T    = x.value

  def name(implicit m: Manifest[Tag]): String = {
    val simpleName = m.runtimeClass.getSimpleName
    simpleName.head.toString.toLowerCase + simpleName.tail.dropRight(1)
  }
}