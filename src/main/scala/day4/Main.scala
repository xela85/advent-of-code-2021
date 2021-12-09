package day4

import cats.effect.{IO, IOApp}
import cats.syntax.traverse._
import fs2.Stream
import text.Loader

import scala.annotation.tailrec

object Main extends IOApp.Simple {
  override def run: IO[Unit] =
    process(Loader.loadFile("day4/data.txt")).flatMap(IO.println)

  def process(lines: Stream[IO, String]): IO[Int] = {
    val inputS = lines.head
      .flatMap(line => Stream.emits(line.split(',')))
      .evalMap(str => IO(str.toInt))
    val boardsS = for {
      chunks <- lines.drop(2).chunkN(6)
      boardStr = chunks.dropRight(1)
      board <- Stream.eval(
        boardStr.toList.traverse(
          _.split(" ").toList.filter(_.nonEmpty).traverse(str => IO(str.toInt))
        )
      )
    } yield Board.build(board)

    for {
      input <- inputS.compile.toList
      boards <- boardsS.compile.toList
    } yield playBingo(boards, input)
  }

  @tailrec
  def playBingo(boards: List[Board], input: List[Int]): Int =
    (input, boards) match {
      case (input :: _, board :: Nil) if board.fill(input).hasWon =>
        board.fill(input).score(input)
      case (input :: rest, board :: Nil) =>
        playBingo(boards, rest)
      case (number :: Nil, _) =>
        boards.headOption.map(_.score(number)).getOrElse(0)
      case (number :: rest, _) =>
        val updated = boards.map(_.fill(number))
        val (_, notWon) = updated.partition(_.hasWon)
        println(s"\n============= $number\n")
        updated.foreach(board => println(board.print + "\n"))
        playBingo(notWon, rest)
      case (Nil, _) => 0
    }

}
