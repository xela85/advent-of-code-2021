package day2

import cats.kernel.Monoid

case class Position(horizontal: Int, depth: Int)

object Position {
  implicit val monoidPosition: Monoid[Position] = Monoid.instance(
    Position(0, 0),
    (a, b) => Position(a.horizontal + b.horizontal, a.depth + b.depth)
  )
}
