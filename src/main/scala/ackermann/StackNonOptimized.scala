package ackermann

object StackNonOptimized {
  def ack(m: Int, n: Int): Int = {
    @annotation.tailrec def loop(expr: Either[(Int, Int), Int], stack: List[Int] = List()): Int = expr match {
      case Left ((0, n)) => loop(Right(n + 1        ),            stack)
      case Left ((m, 0)) => loop(Left((m - 1, 1    )),            stack)
      case Left ((m, n)) => loop(Left((m    , n - 1)), (m - 1) :: stack)
      case Right(n     ) => stack match {
        case m :: mx => loop(Left((m, n)), mx)
        case Nil     => n
      } }
    loop(Left((m, n))) }

  def main(args: Array[String]): Unit = {
    for {
      m <- 0 to 3
      n <- 0 to 4
    } println( s"($m, $n): ${ack(m, n)}" )

    val start = System.currentTimeMillis
    println(s"(4, 1): ${ack(4, 1)})")
    val time = System.currentTimeMillis - start

    println(s"Time: $time")
  }
}