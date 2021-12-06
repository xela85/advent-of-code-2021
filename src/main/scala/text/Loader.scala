package text

import cats.effect.IO
import fs2.Stream
import fs2.io.file.{Files, Path}
import fs2.text.utf8

import java.nio.file.Paths

object Loader {

  def loadFile(name: String): Stream[IO, String] = {
    Files[IO]
      .readAll(
        Path.fromNioPath(
          Paths.get(ClassLoader.getSystemResource(name).toURI)
        )
      )
      .through(utf8.decode)
      .through(fs2.text.lines)
  }

}
