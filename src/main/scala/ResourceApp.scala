import cats.effect.{ExitCode, IO, IOApp, Resource}

object ResourceApp extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    Resource.make(IO.println("open"))(_ => IO.println("close")).use { _ =>
      IO.never
    }
}
