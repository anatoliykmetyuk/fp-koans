package koans.ackermann

trait Test {
  def ack(m: Int, n: Int): Int

  def time(name: String)(f: => Unit): Unit = {
    val start = System.currentTimeMillis
    f
    val t = System.currentTimeMillis - start
    println(s"Time of $name: $t ms")
  }

  def test(): Unit = {
    time("Small Params") {
      for {
        m <- 0 to 3
        n <- 0 to 4
      } println( s"($m, $n): ${ack(m, n)}" )
    }

    time("(4, 1)") {
      println(s"(4, 1): ${ack(4, 1)})")
    }
  }
}