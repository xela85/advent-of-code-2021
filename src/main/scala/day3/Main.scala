package day3

import cats.effect.{IO, IOApp}
import fs2.Stream
import scodec.bits.BitVector
import text.Loader

object Main extends IOApp.Simple {
  override def run: IO[Unit] =
    compute(Loader.loadFile("day3/data.txt")).flatMap(IO.println)

  def compute(stream: Stream[IO, String]): IO[String] =
    stream
      .map(_.toList.map {
        case '1' => true
        case _   => false
      })
      .compile
      .toList
      .map(BoolCount)
      .map { stats =>
        s"most: ${stats.mostCommonValue}, less: ${stats.lessCommonValue}, product: ${stats.mostCommonValue * stats.lessCommonValue}"
      }

}
