package day4

sealed trait CellState

object CellState {
  case object Filled extends CellState
  case object Unfilled extends CellState
}
