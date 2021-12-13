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

  implicit class Sheet(points: Seq[(Int, Int)]) {

    def foldBy(fold: (String, Int)): Seq[(Int, Int)] = {
      fold._1 match {
        case "x" => foldLeft(fold._2)
        case "y" => foldUp(fold._2)
      }
    }

    private def foldUp(y: Int): Seq[(Int, Int)] = {
      points.filter(_._2 < y).appendedAll(
        points.filter(_._2 > y)
          .map(point => (point._1, Math.abs(y * 2 - point._2)))
      ).distinct
    }

    private def foldLeft(x: Int): Seq[(Int, Int)] = {
      points.filter(_._1 < x).appendedAll(
        points.filter(_._1 > x)
          .map(point => (Math.abs(x * 2 - point._1), point._2))
      ).distinct
    }
  }
}
