package day1

import cats.syntax.foldable._
import cats.effect.{IO, IOApp}
import fs2.Stream
import text.Loader

object Main extends IOApp.Simple {
  override def run: IO[Unit] = {
    val inputStream = Loader
      .loadFile("day1/data.txt")
      .evalMap(str => IO(str.toInt))
    compute(inputStream).flatMap(IO.println)
  }

  def compute(stream: Stream[IO, Int]): IO[Int] =
    stream
      .sliding(3)
      .map(_.combineAll)
      .zipWithPrevious
      .foldMap {
        case (Some(previous), i) if i >= previous => 1
        case _                                    => 0
      }
      .compile
      .lastOrError

}
