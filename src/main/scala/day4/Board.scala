package day4

final case class Board(rows: List[List[Cell]]) {

  val columns: List[List[Cell]] = rows.head.indices.toList.map { columnI =>
    rows.map(_(columnI))
  }

  def fill(input: Int): Board = Board(rows.map(_.map(_.fill(input))))
  def hasWon: Boolean = rows.exists(Cell.hasWon) || columns.exists(Cell.hasWon)

  def score(input: Int): Int = {
    val sum =
      rows.map(_.filter(_.state == CellState.Unfilled).map(_.value).sum).sum
    sum * input
  }

  def print: String = rows.map(_.map(_.print).mkString(" ")).mkString("\n")

}

object Board {

  def build(input: List[List[Int]]): Board = Board(
    input.map(_.map[Cell](Cell(_, CellState.Unfilled)))
  )

}
