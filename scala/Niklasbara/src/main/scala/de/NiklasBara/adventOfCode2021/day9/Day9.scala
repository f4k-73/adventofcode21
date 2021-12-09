package de.NiklasBara.adventOfCode2021.day9

import java.nio.charset.StandardCharsets
import scala.io.Source

object Day9 extends App {
  val INPUT_PATH = Inputs.local
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().toSeq
  source.close()

  private val value: Seq[Seq[Int]] = lines.map(_.toCharArray.map(_.asDigit).toSeq)

  var cells: Seq[Cell] = Seq()
  for (y <- value.indices) {
    for (x <- 0 until value.head.size) {
      val top = value.getNeighbourOrElse(y, x, OffsetsYX.top, Int.MaxValue)
      val left = value.getNeighbourOrElse(y, x, OffsetsYX.left, Int.MaxValue)
      val bottom = value.getNeighbourOrElse(y, x, OffsetsYX.bottom, Int.MaxValue)
      val right = value.getNeighbourOrElse(y, x, OffsetsYX.right, Int.MaxValue)

      cells = cells.appended(Cell(value.get(y, x), top, left, right, bottom))
    }
  }


  println("part1:", cells.filter(_.isLowPoint).map(_.value + 1).sum)



  implicit class Better2dSeq[T](val s: Seq[Seq[T]]) {
    def getNeighbourOrElse(y: Int, x: Int, offset: (Int, Int), other: T): T = {
      val targetY = y + offset._1
      val targetX = x + offset._2

      if (targetX < 0 || targetY < 0) {
        other
      } else if (targetY > s.length - 1 || targetX > s(targetY).length - 1) {
        other
      } else {
        s(targetY)(targetX)
      }
    }

    def get(y: Int, x: Int) = s(y)(x)
  }
}

case class Cell(
                 value: Int,
                 neighbourTop: Int,
                 neighbourLeft: Int,
                 neighbourRight: Int,
                 neighbourBottom: Int,
               ) {

  def isLowPoint = {
    Seq(neighbourTop, neighbourLeft, neighbourBottom, neighbourRight).forall(_ > value)
  }
}

object OffsetsYX extends Enumeration {
  val top: (Int, Int) = (-1, 0)
  val left: (Int, Int) = (0, -1)
  val bottom: (Int, Int) = (1, 0)
  val right: (Int, Int) = (0, 1)
}

object Inputs extends Enumeration {
  type Inputs = Value
  val test: Inputs.Value = Value("src/main/resources/day9/test_input.txt")
  val local: Inputs.Value = Value("src/main/resources/day9/input.txt")
  val shared: Inputs.Value = Value("../../data/day9.txt")
}
