package day2

import cats.Monoid
import cats.effect.{IO, IOApp}
import fs2.Stream
import fs2.io.file.{Files, Path}
import fs2.text.utf8

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

  case class FoldState(currentAim: Aim)

  def compute(stream: Stream[IO, Order]): IO[Int] =
    stream
      .fold() {}
      .fold(Aim(0)) { case (a, b) =>
        ???
      }
      .map(_.toPosition)
      .foldMonoid
      .compile
      .lastOrError
      .map(pos => pos.depth * pos.horizontal)
}
