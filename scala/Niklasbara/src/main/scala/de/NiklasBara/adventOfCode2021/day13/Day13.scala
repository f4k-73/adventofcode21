package de.NiklasBara.adventOfCode2021.day13

import de.NiklasBara.adventOfCode2021.input.{AdventDays, InputReader, InputTypes}

object Day13 extends App {

  val inputs = InputReader(AdventDays.THIRTEEN, InputTypes.local).lines.filterNot(_.isEmpty)

  val points: Seq[(Int, Int)] = inputs.filterNot(_.startsWith("fold along"))
    .map(_.split(",").map(str => str.toInt))
    .map(arr => (arr(0), arr(1)))

  val folds: Seq[(String, Int)] = inputs.filter(_.startsWith("fold along"))
    .map(_.replace("fold along", "").trim)
    .map(_.split("="))
    .map(arr => (arr(0), arr(1).toInt))

  println("part1:", points.foldBy(folds.head).length)
  println("part2:")
  println(points.foldByInstructions(folds).toOutput)

  implicit class Sheet(points: Seq[(Int, Int)]) {

    def fillWithString(input: Seq[Int], xSize: Int): String = Seq.fill(xSize)(SheetChars.empty)
      .zipWithIndex
      .map(strAndIndex => if (input.contains(strAndIndex._2)) {
        SheetChars.point
      } else {
        strAndIndex._1
      }).mkString(SheetChars.empty.toString)

    def toOutput: String = points.groupMap(_._2)(_._1)
      .toSeq
      .sortBy(_._1)
      .map(entry => fillWithString(entry._2, points.map(_._1).max))
      .mkString(SheetChars.newLine.toString)

    def foldByInstructions(folds: Seq[(String, Int)]): Seq[(Int, Int)] = if (folds.isEmpty) {
      points
    } else {
      points.foldBy(folds.head).foldByInstructions(folds.drop(1))
    }

    def foldBy(fold: (String, Int)): Seq[(Int, Int)] = fold._1 match {
      case "x" => foldLeft(fold._2)
      case "y" => foldUp(fold._2)
    }

    private def foldUp(y: Int): Seq[(Int, Int)] = points.filter(_._2 < y)
      .appendedAll(
        points.filter(_._2 > y)
          .map(point => (point._1, Math.abs(y * 2 - point._2)))
      ).distinct

    private def foldLeft(x: Int): Seq[(Int, Int)] = points.filter(_._1 < x)
      .appendedAll(
        points.filter(_._1 > x)
          .map(point => (Math.abs(x * 2 - point._1), point._2))
      ).distinct
  }
}

object SheetChars extends Enumeration {
  type SheetChars = Value
  val point: SheetChars.Value = Value("#")
  val empty: SheetChars.Value = Value(" ")
  val newLine: SheetChars.Value = Value("\n")
}