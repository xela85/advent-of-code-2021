package day3

import cats.Foldable
import fs2.{Pure, Stream}
import cats.syntax.foldable._
import cats.syntax.traverse._
import cats.syntax.monad._
import day3.ValueWanted.boolean
import scodec.bits.BitVector

import scala.annotation.tailrec

sealed trait ValueWanted

object ValueWanted {
  case object MostCommon extends ValueWanted
  case object LessCommon extends ValueWanted
  def boolean(valueWanted: ValueWanted): Boolean = valueWanted match {
    case MostCommon => true
    case LessCommon => false
  }
}

final case class BoolCount(lines: List[List[Boolean]]) {

  def mostCommonValue: Long = boolToDec(
    mostCommonRec(lines, ValueWanted.MostCommon, 0)
  )
  def lessCommonValue: Long = boolToDec(
    mostCommonRec(lines, ValueWanted.LessCommon, 0)
  )

  private def boolToDec(bool: List[Boolean]): Long =
    bool.reverse.zipWithIndex.foldMap {
      case (true, i)  => math.pow(2, i).toLong
      case (false, i) => 0L
    }

  @tailrec
  private def mostCommonRec(
      lines: List[List[Boolean]],
      valueWanted: ValueWanted,
      index: Int
  ): List[Boolean] = {
    val seq = lines.flatMap(_.lift(index))
    seq match {
      case _ :: Nil | Nil => lines.head
      case res if res.count(_ == true) * 2 >= res.size =>
        mostCommonRec(
          lines.filter(_.lift(index).contains(boolean(valueWanted))),
          valueWanted,
          index + 1
        ) // true vals
      case _ =>
        mostCommonRec(
          lines.filter(_.lift(index).contains(!boolean(valueWanted))),
          valueWanted,
          index + 1
        ) // false vals
    }
  }

}
