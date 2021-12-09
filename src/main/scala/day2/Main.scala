package day2

import cats.effect.{IO, IOApp}
import fs2.Stream
import text.Loader
import monocle.syntax.all._

object Main extends IOApp.Simple {

  override def run: IO[Unit] = {
    val inputStream = Loader
      .loadFile("day2/data.txt")
      .evalMap(line => IO.fromEither(Order.parse(line)))
    compute(inputStream).flatMap(IO.println)
  }

  case class AimState(position: Position, aim: Int)

  def compute(stream: Stream[IO, Order]): IO[Int] =
    stream
      .fold(AimState(Position(0, 0), 0)) {
        case (state, Order(length, Direction.Up)) =>
          state.focus(_.aim).modify(_ - length)
        case (state, Order(length, Direction.Down)) =>
          state.focus(_.aim).modify(_ + length)
        case (state, Order(length, Direction.Forward)) =>
          state
            .focus(_.position.depth)
            .modify(_ + state.aim * length)
            .focus(_.position.horizontal)
            .modify(_ + length)

      }
      .compile
      .lastOrError
      .map(_.position)
      .map(pos => pos.depth * pos.horizontal)
}
