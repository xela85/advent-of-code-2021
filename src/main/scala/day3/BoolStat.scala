package day3

import cats.Monoid
import cats.syntax.foldable._
import cats.derived._

import scala.math.pow

final case class BoolStat(stats: Map[Int, BoolCount]) {
  def gammaRate: Long = BoolStat.toDecimal(stats.view.mapValues(_.mostCommonValue).toMap)
  def epsilonRate: Long = BoolStat.toDecimal(stats.view.mapValues(_.lessCommonValue).toMap)
}
object BoolStat {

  implicit val monoidBoolStat: Monoid[BoolStat] = semiauto.monoid

  private def toDecimal(value: Map[Int, Int]) = value.toList.foldMap {
    case (index, value) => pow(value * 2, index).toLong * value
  }

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
