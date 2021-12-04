package de.edean.aoc2021.day4

import scala.io.Source

object Day4 extends App {
  val COLUMN_ROW_AMOUNT = 5
  val INPUT_PATH = if (System.getProperty("os.name").startsWith("Mac") &&
    System.getenv("USER").toLowerCase.equals("edean"))
    "src/main/resources/input4.txt"
  else "../../data/day4.txt"

  val input = Source.fromFile(INPUT_PATH, "UTF-8").getLines().toSeq

  val numbersDrawn = input.head.split(",").map(_.toInt)


  case class BingoBoard(rows: Seq[Seq[(Int, Boolean)]], lastDrawn: Int = 0) {
    def apply(drawnNumber: Int): BingoBoard = {
     this.copy(
        rows = rows.map(_.map({ case (number, isDrawn) => (number, drawnNumber.equals(number) || isDrawn) })),
        lastDrawn = drawnNumber
     )
    }

    def apply(drawnNumbers: Seq[Int]): BingoBoard = {
      if (hasWon) {
        this
      } else if (drawnNumbers.size == 1) {
        this.copy(lastDrawn = drawnNumbers.head) (drawnNumbers.head)
      } else {
        this.copy(lastDrawn = drawnNumbers.head) (drawnNumbers.head)(drawnNumbers.drop(1))
      }
    }

    val hasWon = {
      val boolRows = rows.map(_.map(_._2))

      def hasAnyRowWon(boolRows: Seq[Seq[Boolean]] = boolRows) = boolRows.map(_
        .reduce((left, right) => left && right)) // has the row won?
        .reduce((left, right) => left || right) // has any row won?
      def hasAnyColumnWon = boolRows.reduce((left, right) => left.zip(right).map({case (left, right) => left && right})).reduce((left, right) => left || right)
      /* Why don't you want my fucking diagonals
      def fromLeftToRightCornerWon(boolRows: Seq[Seq[Boolean]]) = {
        (0 to 4).map(i => boolRows(i)(i)).reduce((left, right) => left && right)
      }
      def hasAnyDiagonalWon = fromLeftToRightCornerWon(boolRows) || fromLeftToRightCornerWon(boolRows.map(_.reverse))
      */
      hasAnyRowWon() || hasAnyColumnWon //|| hasAnyDiagonalWon
    }

    val score = if (!hasWon) 0 else {
      rows.flatten.filter(!_._2).map(_._1).sum * lastDrawn
    }

    override def toString: String = {
      rows.map(_.map({ case (num, drawn) => if (drawn) f"($num%2d)" else f" $num%2d " }).mkString).mkString("\n")
    }
  }

  val bingoboards = input.drop(2) // drop drawn numbers and the empty line afterwards
    .filter(!_.isEmpty)
    .sliding(COLUMN_ROW_AMOUNT, COLUMN_ROW_AMOUNT)
    .map(_.map(_.split(" ").filter(!_.isEmpty).map(_.toInt).map((_, false)).toSeq))
    .map(BingoBoard(_)).toSeq

  println(numbersDrawn.mkString(", "))
  println("---")
  def findWinner(boards:        Seq[BingoBoard] = bingoboards,
                 numbersDrawn:  Seq[Int]        = numbersDrawn,
                 currentNumber: Int             = numbersDrawn.head): BingoBoard = {
    val newBoards     = boards.map(_(currentNumber))
    val winningBoards = newBoards.filter(_.hasWon)
    if (winningBoards.isEmpty) {
      val remainingNumbers = numbersDrawn.drop(1)
      findWinner(newBoards, remainingNumbers, remainingNumbers.head)
    } else {
      winningBoards.head
    }
  }
  val wonBoard = findWinner()
  println(wonBoard)
  println(wonBoard.score)

  // --- part 2
  println("---")

  def findLoser(boards:        Seq[BingoBoard] = bingoboards,
                numbersDrawn:  Seq[Int]        = numbersDrawn,
                currentNumber: Int             = numbersDrawn.head): BingoBoard = {
    val newBoards = boards.map(_ (currentNumber))
    val winningBoards = newBoards.filter(_.hasWon)
    if (newBoards.size == winningBoards.size) {
      boards.filter(!_.hasWon).map(_ (currentNumber)).head
    } else {
      val remainingNumbers = numbersDrawn.drop(1)
      findLoser(newBoards, remainingNumbers, remainingNumbers.head)
    }
  }

  val loserBoard = findLoser()
  println(loserBoard)
  println(loserBoard.score)
}
