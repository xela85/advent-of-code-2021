package day2

import cats.effect.{IO, IOApp}
import fs2.Stream
import fs2.io.file.{Files, Path}
import fs2.text.utf8
import monocle.macros.syntax.lens._

import java.nio.file.Paths

object Main extends IOApp.Simple {

  override def run: IO[Unit] = {
    val inputStream = Files[IO]
      .readAll(
        Path.fromNioPath(
          Paths.get(ClassLoader.getSystemResource("day2/data.txt").toURI)
        )
      )
      .through(utf8.decode)
      .through(fs2.text.lines)
      .evalMap(line => IO.fromEither(Order.parse(line)))
    compute(inputStream).flatMap(IO.println)
  }

  case class AimState(position: Position, aim: Int)

  def compute(stream: Stream[IO, Order]): IO[Int] =
    stream
      .fold(AimState(Position(0, 0), 0)) {
        case (state, Order(length, Direction.Up)) =>
          state.lens(_.aim).modify(_ - length)
        case (state, Order(length, Direction.Down)) =>
          state.lens(_.aim).modify(_ + length)
        case (state, Order(length, Direction.Forward)) =>
          state
            .lens(_.position.depth)
            .modify(_ + state.aim * length)
            .lens(_.position.horizontal)
            .modify(_ + length)

      }
      .compile
      .lastOrError
      .map(_.position)
      .map(pos => pos.depth * pos.horizontal)
}
