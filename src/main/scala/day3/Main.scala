package day3

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
          Paths.get(ClassLoader.getSystemResource("day3/data.txt").toURI)
        )
      )
      .through(utf8.decode)
      .through(fs2.text.lines)
    compute(inputStream).flatMap(IO.println)
  }

  def compute(stream: Stream[IO, String]): IO[String] = {
    stream
      .map(BoolStat.fromLine)
      .compile
      .foldMonoid
      .map { stats =>
        s"epsilon: ${stats.epsilonRate}, gamma: ${stats.gammaRate}, total: ${stats.gammaRate * stats.epsilonRate}"
      }
  }

}
