package ackermann

trait Test {
  def ack(m: Int, n: Int): Int

  def test(): Unit = {
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