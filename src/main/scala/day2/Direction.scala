package day2

sealed trait Direction

object Direction {
  case object Up extends Direction
  case object Forward extends Direction
  case object Down extends Direction
  def parse(str: String): Option[Direction] = str match {
    case "forward" => Some(Forward)
    case "down"    => Some(Down)
    case "up"      => Some(Up)
    case _         => None
  }

}
