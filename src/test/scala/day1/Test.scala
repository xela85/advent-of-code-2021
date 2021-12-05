package day1

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import day1.Main
import org.scalatest.flatspec.{AnyFlatSpec, AsyncFlatSpec}
import org.scalatest.matchers.should.Matchers

class Test extends AsyncFlatSpec with AsyncIOSpec with Matchers {

  "code" should "work" in {
    Main
      .compute(
        fs2.Stream
          .emits[IO, Int](
            List(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
          )
      )
      .asserting(_ shouldBe 6)
  }

}
