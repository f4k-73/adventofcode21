package de.NiklasBara.adventOfCode2021.day9

import java.nio.charset.StandardCharsets
import scala.io.Source

object Day9 extends App {
  val INPUT_PATH = Inputs.local
  val source = Source.fromFile(INPUT_PATH.toString, StandardCharsets.UTF_8.toString)
  val lines = source.getLines().toSeq
  source.close()

  val value: Seq[Seq[Int]] = lines.map(_.toCharArray.map(_.asDigit).toSeq)


  val offsets = Seq((-1, 0), (0, -1), (1, 0), (0, 1))


  println("part1:", value.lowPoints().map(_._1 + 1).sum)


  implicit class Better2dSeq(val s: Seq[Seq[Int]]) {
    def getCell(y: Int, x: Int): (Int, Int, Int) = {
      if (x < 0 || y < 0) {
        (Int.MaxValue, y, x)
      } else if (y > s.length - 1 || x > s(y).length - 1) {
        (Int.MaxValue, y, x)
      } else {
        (s(y)(x), y, x)
      }
    }

    def getNeighbour(y: Int, x: Int, offset: (Int, Int)) = getCell(y + offset._1, x + offset._2)

    def isLowPoint(y: Int, x: Int) = offsets.map(getNeighbour(y, x, _)).map(_._1).forall(_ > getCell(y, x)._1)

    def lowPoints() = s.zipWithIndex
      .flatMap(lineAndIndex => lineAndIndex._1.zipWithIndex
        .filter(cellAndIndex => isLowPoint(lineAndIndex._2, cellAndIndex._2))
        .map(cellAndIndex => (cellAndIndex._1, lineAndIndex._2, cellAndIndex._2))
      )

    def get(y: Int, x: Int) = s(y)(x)
  }
}

object Inputs extends Enumeration {
  type Inputs = Value
  val test: Inputs.Value = Value("src/main/resources/day9/test_input.txt")
  val local: Inputs.Value = Value("src/main/resources/day9/input.txt")
  val shared: Inputs.Value = Value("../../data/day9.txt")
}
