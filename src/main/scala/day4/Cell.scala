package day4

final case class Cell(value: Int, state: CellState) {
  def fill(number: Int) = state match {
    case CellState.Filled                      => this
    case CellState.Unfilled if number == value => copy(state = CellState.Filled)
    case CellState.Unfilled                    => this
  }

  def print: String = (state match {
    case CellState.Filled   => "X"
    case CellState.Unfilled => value.toString
  }).padTo(2, ' ')

}

object Cell {
  def score(row: List[Cell]) =
    if (hasWon(row)) row.map(_.value).sum
    else 0

  def hasWon(row: List[Cell]) = row.forall(_.state == CellState.Filled)

}
