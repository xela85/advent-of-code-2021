package day3

import cats.Monoid
import cats.syntax.foldable._
import cats.derived._

import scala.math.pow

final case class BoolStat(stats: Map[Int, BoolCount]) {

  def gammaRate: Long =
    stats.toList.foldMap { case (index, boolCount) =>
      pow(
        boolCount.mostCommonValue * 2,
        index
      ).toLong * boolCount.mostCommonValue
    }

  def epsilonRate: Long = stats.toList.foldMap { case (index, boolCount) =>
    pow(boolCount.lessCommonValue * 2, index).toLong * boolCount.lessCommonValue
  }
}
object BoolStat {

  implicit val monoidBoolStat: Monoid[BoolStat] = semiauto.monoid

  def fromLine(line: String): BoolStat =
    BoolStat(
      line.reverse.zipWithIndex.toList.foldMap { case (letter, index) =>
        val count = letter match {
          case '1' => BoolCount(1, 0)
          case '0' => BoolCount(0, 1)
          case _   => BoolCount(0, 0)
        }
        Map(index -> count)
      }
    )

}
