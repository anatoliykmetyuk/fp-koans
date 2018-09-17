package ackermann

import collection.immutable.Stack

object StackMain {
  def ack(m: Int, n: Int): Int = {
    @annotation.tailrec def loop(expr: Either[(Int, Int), Int], stack: Stack[Int] = Stack()): Int = expr match {
      case Left ((0, n)) => loop(Right(n + 1), stack)
      case Left ((m, n)) => loop(Right(2), stack.pushAll { List.fill(n)(m - 1) ++ (m - 2 to 0 by -1) })
      case Right(n     ) => if (stack.isEmpty) n else stack.pop2 match { case (a, s2) => loop(Left((a, n)), s2) } }
    loop(Left(m, n)) }

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