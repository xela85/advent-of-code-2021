package day3

import cats.Monoid
import cats.derived.semiauto

final case class BoolCount(trueVals: Int, falseVals: Int) {
  def mostCommonValue: Int = if (trueVals > falseVals) 1 else 0
  def lessCommonValue: Int = 1 - mostCommonValue
}

object BoolCount {
  implicit val monoidBoolCount: Monoid[BoolCount] = semiauto.monoid
}
