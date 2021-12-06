package day3

import cats.effect.{IO, IOApp}
import fs2.Stream
import text.Loader

object Main extends IOApp.Simple {
  override def run: IO[Unit] =
    compute(Loader.loadFile("day3/data.txt")).flatMap(IO.println)

  def compute(stream: Stream[IO, String]): IO[String] =
    stream
      .map(BoolStat.fromLine)
      .compile
      .foldMonoid
      .map { stats =>
        s"epsilon: ${stats.epsilonRate}, gamma: ${stats.gammaRate}, total: ${stats.gammaRate * stats.epsilonRate}"
      }

}
