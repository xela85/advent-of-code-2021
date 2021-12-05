package day3

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import day3.Main
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class Test extends AsyncFlatSpec with AsyncIOSpec with Matchers {

  "code" should "work" in {
    Main
      .compute(
        fs2.Stream
          .emits[IO, String](
            List(
              "00100",
              "11110",
              "10110",
              "10111",
              "10101",
              "01111",
              "00111",
              "11100",
              "10000",
              "11001",
              "00010",
              "01010"
            )
          )
      )
      .asserting(
        _ shouldBe "epsilon: 9, gamma: 22, total: 198"
      )
  }

}
