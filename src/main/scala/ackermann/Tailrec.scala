package ackermann

object Tailrec extends App with Test {
  def ack(m: Int, n: Int): Int = {
    @annotation.tailrec def loop(mx: List[Int], n: Int): Int = (mx, n) match {
      case (0 :: ms, n) => loop(              ms, n + 1)
      case (m :: ms, 0) => loop(m - 1      :: ms, 1    )
      case (m :: ms, n) => loop(m :: m - 1 :: ms, n - 1)
      case (Nil,     n) => n
    }
    loop(m :: Nil, n)
  }

  test()
}