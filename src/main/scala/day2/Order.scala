package day2

import cats.syntax.either._

final case class Order(length: Int, direction: Direction) {
  def toPosition: Position = direction match {
    case Direction.Forward => Position(length, 0)
    case Direction.Down    => Position(0, length)
    case Direction.Up      => Position(0, -length)
  }
}

object Order {

  def parse(str: String): Either[Throwable, Order] = str.split(" ") match {
    case Array(directionStr, lengthStr) =>
      for {
        length <- Either.catchNonFatal(lengthStr.toInt)
        direction <- Direction
          .parse(directionStr)
          .toRight(new Throwable(s"invalid direction $directionStr"))
      } yield Order(length, direction)
    case _ => Left(new Throwable(s"Invalid input line $str"))
  }

}
